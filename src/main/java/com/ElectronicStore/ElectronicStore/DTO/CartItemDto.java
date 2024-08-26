package com.ElectronicStore.ElectronicStore.DTO;


import com.ElectronicStore.ElectronicStore.Model.Cart;
import com.ElectronicStore.ElectronicStore.Model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CartItemDto {
    private long cartItemId;
    private ProductDto product;
    private int quantity;
    private int totalPrice;
}
