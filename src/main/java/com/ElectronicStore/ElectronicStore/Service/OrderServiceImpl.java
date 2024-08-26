package com.ElectronicStore.ElectronicStore.Service;

import com.ElectronicStore.ElectronicStore.DTO.CreateOrderRequest;
import com.ElectronicStore.ElectronicStore.DTO.OrdersDto;
import com.ElectronicStore.ElectronicStore.DTO.PageableResponse;
import com.ElectronicStore.ElectronicStore.DTO.UpdateOrderRequest;
import com.ElectronicStore.ElectronicStore.Exception.BadApiRequest;
import com.ElectronicStore.ElectronicStore.Exception.ResourceNotFoundException;
import com.ElectronicStore.ElectronicStore.Model.*;
import com.ElectronicStore.ElectronicStore.Repositories.CartRepositories;
import com.ElectronicStore.ElectronicStore.Repositories.OrderRepository;
import com.ElectronicStore.ElectronicStore.Repositories.ProductRepository;
import com.ElectronicStore.ElectronicStore.Repositories.UserRepositories;
import com.ElectronicStore.ElectronicStore.Service.Interface.OrderService;
import com.ElectronicStore.ElectronicStore.util.Pagination;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepositories userRepositories;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepositories cartRepositories;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrdersDto createOrder(CreateOrderRequest createOrderRequest) {
   long userId = createOrderRequest.getUserId();
   long cartId = createOrderRequest.getCartId();
    User user =userRepositories.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found exception"));
    System.out.println("CartId:"+cartId);
    Cart cart = cartRepositories.findById(cartId).orElseThrow(()->new ResourceNotFoundException("cart not found exception"));
    List<CartItem> cartItems = cart.getItems();

    if(cartItems.size() <=0)
        throw new BadApiRequest("Invalid number of items in cart");

        Orders order = Orders.builder()
                .phoneNo(createOrderRequest.getPhoneNo())
                .address(createOrderRequest.getAddress())
                .orderDate(new Date())
                .deliverDate(null)
                .paymentStatus(createOrderRequest.getPaymentStatus())
                .orderStatus(createOrderRequest.getOrderStatus())
                .user(user).build();

        AtomicLong orderAmount= new AtomicLong(0);
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            orderAmount.set( orderAmount.get() + cartItem.getTotalPrice());
          OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity()*cartItem.getProduct().getProductDiscountPrice())
                    .order(order).build();
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderAmount(orderAmount.get());
        order.setOrderItems(orderItems);
        cart.getItems().clear();
        cartRepositories.save(cart);
        return modelMapper.map(orderRepository.save(order),OrdersDto.class);
    }



    @Override
    public String removeOrder(long orderId) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order not found"));
       long differenceInDays = (((new Date()).getTime() - orders.getOrderDate().getTime())/1000/60/60/24);

       if(differenceInDays < 3){
           return "Cancel order is valid only 2 days";
       }
       orderRepository.delete(orders);
       return "Order cancel successfully";






    }

    @Override
    public List<OrdersDto> getOrdersOfUser(long userid) {
       User user =  userRepositories.findById(userid).orElseThrow(()-> new ResourceNotFoundException("User not found !!"));
       List<Orders> orders = orderRepository.findByUser(user);
       List<OrdersDto> ordersDtos = orders.stream().map(ord->modelMapper.map(ord,OrdersDto.class)).collect(Collectors.toList());
       return ordersDtos;
    }

    @Override
    public PageableResponse<OrdersDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equals("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Orders> page = orderRepository.findAll(pageable);
        return Pagination.getPageableResponse(page,OrdersDto.class);
    }
}
