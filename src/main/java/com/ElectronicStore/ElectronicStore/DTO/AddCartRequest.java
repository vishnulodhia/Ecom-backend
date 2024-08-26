package com.ElectronicStore.ElectronicStore.DTO;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder


public class AddCartRequest {

    @NotBlank
    private long productId;
    @NotBlank
    private  int quantity;

}
