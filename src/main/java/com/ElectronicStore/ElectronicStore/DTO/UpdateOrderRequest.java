package com.ElectronicStore.ElectronicStore.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UpdateOrderRequest {

    private String orderStatus;
    private String paymentStatus;
    @NotBlank
    private String address;
    @Size(min=10, max=10, message = "Invalid Phone No !!")
    @NotBlank
    private String phoneNo;
}
