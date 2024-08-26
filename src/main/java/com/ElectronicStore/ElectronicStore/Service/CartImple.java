package com.ElectronicStore.ElectronicStore.Service;

import com.ElectronicStore.ElectronicStore.DTO.AddCartRequest;
import com.ElectronicStore.ElectronicStore.DTO.CartDto;
import com.ElectronicStore.ElectronicStore.Exception.ResourceNotFoundException;
import com.ElectronicStore.ElectronicStore.Model.Cart;
import com.ElectronicStore.ElectronicStore.Model.CartItem;
import com.ElectronicStore.ElectronicStore.Model.Product;
import com.ElectronicStore.ElectronicStore.Model.User;
import com.ElectronicStore.ElectronicStore.Repositories.CartItemRepository;
import com.ElectronicStore.ElectronicStore.Repositories.CartRepositories;
import com.ElectronicStore.ElectronicStore.Repositories.ProductRepository;
import com.ElectronicStore.ElectronicStore.Repositories.UserRepositories;
import com.ElectronicStore.ElectronicStore.Service.Interface.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class CartImple implements CartService {

    @Autowired
   private ProductRepository productRepository;

    @Autowired
    private CartRepositories cartRepositories;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepositories userRepositories;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDto addItemToCart(long userId, AddCartRequest request) {
     int quantity = request.getQuantity();
     long productId = request.getProductId();
     System.out.println("productId: 1 "+productId);
     Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product not found in database"));
     System.out.println("productId: 2 "+productId);
     if(quantity>product.getProductQuantity())
      return null;

     User user =  userRepositories.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found in database"));
     Optional<Cart> getCart  = cartRepositories.findByuser(user);

     Cart cart ;
     AtomicBoolean updated = new AtomicBoolean(false);

     if(!getCart.isPresent()) {

      cart = new Cart();
      cart.setUser(user);
     }
     else {

       cart = getCart.get();
      List<CartItem> Items = cart.getItems();
      List<CartItem> updatedItems = Items.stream().map(items->{

       if(items.getProduct().getProductId()==productId){
        items.setQuantity(quantity);
        items.setTotalPrice(product.getProductDiscountPrice());
        updated.set(true);
       }
     return items;
      }).collect(Collectors.toList());
      System.out.println("updated: "+ updated);
      if(updated.get()==true)
      cart.setItems(updatedItems);

     }


     if(updated.get()==false){
      CartItem cartItem = CartItem.builder()
              .quantity(quantity)
              .totalPrice(quantity*product.getProductDiscountPrice())
              .cart(cart)
              .product(product)
              .build();
      cart.getItems().add(cartItem);
     }

        return modelMapper.map(cartRepositories.save(cart),CartDto.class);
    }

    @Override
    public void removeItemFromCart(long cartItemId) {
     CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(()-> new ResourceNotFoundException("CartItem not found"));
     cartItemRepository.delete(cartItem);
    }

    @Override
    public void clearCart(long UserId) {

     User user = userRepositories.findById(UserId).orElseThrow(()-> new ResourceNotFoundException("User not found exception"));
     Cart cart = cartRepositories.findByuser(user).orElseThrow(()-> new ResourceNotFoundException("Cart of given user not found"));
     cart.getItems().clear();
     cartRepositories.save(cart);

    }

 @Override
 public CartDto fetchCart(long userId) {
  User user = userRepositories.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found exception"));
 Cart cart =  cartRepositories.findByuser(user).orElseThrow(()-> new ResourceNotFoundException("Cart of given user not found"));


  return modelMapper.map(cart,CartDto.class);
    }


}
