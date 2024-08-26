package com.ElectronicStore.ElectronicStore.DTO;


import com.ElectronicStore.ElectronicStore.Model.CartItem;
import com.ElectronicStore.ElectronicStore.Model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CartDto {

    private long cartId;
    private UserDto user;
    private List<CartItemDto> items  = new ArrayList<>();
}
