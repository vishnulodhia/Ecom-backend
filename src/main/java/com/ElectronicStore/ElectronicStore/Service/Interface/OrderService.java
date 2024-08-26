package com.ElectronicStore.ElectronicStore.Service.Interface;


import com.ElectronicStore.ElectronicStore.DTO.CreateOrderRequest;
import com.ElectronicStore.ElectronicStore.DTO.OrdersDto;
import com.ElectronicStore.ElectronicStore.DTO.PageableResponse;
import com.ElectronicStore.ElectronicStore.DTO.UpdateOrderRequest;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.List;

public interface OrderService {
//    create order
    OrdersDto createOrder(CreateOrderRequest createOrderRequest);
    //    remove order
    String removeOrder(long orderId);
//    get order of user
    public List<OrdersDto> getOrdersOfUser(long userid);
    PageableResponse<OrdersDto> getOrders(int pageNumber,int pageSize,String sortBy,String sortDir);
//    get orders
}
