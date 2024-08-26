package com.ElectronicStore.ElectronicStore.DTO;

import com.ElectronicStore.ElectronicStore.Model.OrderItem;
import com.ElectronicStore.ElectronicStore.Model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class OrdersDto {

    private long orderId;
    private String orderStatus="PENDING";
    private String paymentStatus="UNPAID";
    private long orderAmount;
    private String address;
    @Size(min=10, max=10, message = "Invalid Phone No !!")
    @NotBlank
    private String phoneNo;
    private Date orderDate= new Date();
    private Date deliverDate;
    private UserDto userDto;
    private List<OrderItemDto> orderItems = new ArrayList<>();

}
