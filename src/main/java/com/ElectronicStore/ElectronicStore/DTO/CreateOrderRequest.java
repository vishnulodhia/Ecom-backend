package com.ElectronicStore.ElectronicStore.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {
    @NotNull(message = "cart id is required")
    private long cartId;
    @NotNull(message = "user id is required")
    private long userId;
    private String orderStatus="PENDING";
    private String paymentStatus="UNPAID";
    @NotBlank
    private String address;
    @Size(min=10, max=10, message = "Invalid Phone No !!")
    @NotBlank
    private String phoneNo;
}
