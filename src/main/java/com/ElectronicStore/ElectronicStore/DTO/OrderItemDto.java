package com.ElectronicStore.ElectronicStore.DTO;

import com.ElectronicStore.ElectronicStore.Model.Orders;
import com.ElectronicStore.ElectronicStore.Model.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {

    private long orderItemId;
    private int quantity;
    private int totalPrice;
    private ProductDto product;

}
