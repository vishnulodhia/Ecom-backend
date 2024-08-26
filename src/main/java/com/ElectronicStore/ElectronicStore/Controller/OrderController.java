package com.ElectronicStore.ElectronicStore.Controller;


import com.ElectronicStore.ElectronicStore.DTO.*;
import com.ElectronicStore.ElectronicStore.Service.Interface.CategoryService;
import com.ElectronicStore.ElectronicStore.Service.Interface.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/Order")
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping(value= "/createOrder")
    public ResponseEntity<OrdersDto> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest){
        return new ResponseEntity<>(orderService.createOrder(createOrderRequest),HttpStatus.CREATED);
    }

    @DeleteMapping("/removeOrder/{orderId}")
    public ResponseEntity<APiResponseMessage> removeOrder(@PathVariable long orderId) {
        APiResponseMessage message = APiResponseMessage.builder().message(orderService.removeOrder(orderId)).status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @GetMapping("/getUserOrder/{userId}")
    public ResponseEntity<List<OrdersDto>> getUserOrder(@PathVariable long userId) {
        return new ResponseEntity<>(orderService.getOrdersOfUser(userId),HttpStatus.OK);
    }

    @GetMapping("/getOrders/{userId}")
    public ResponseEntity<PageableResponse<OrdersDto>> getOrders(@PathVariable long userId, @RequestParam(value = "PageNumber" , defaultValue = "0" , required = false) int PageNumber, @RequestParam(value = "PageSize" , defaultValue = "10" , required = false) int PageSize, @RequestParam(value = "SortBy" , defaultValue = "name" , required = false) String SortBy, @RequestParam(value = "SortDir" , defaultValue = "asc" , required = false) String SortDir) {
        return new ResponseEntity<>(orderService.getOrders(PageNumber,PageSize,SortBy,SortDir),HttpStatus.OK);
    }

}
