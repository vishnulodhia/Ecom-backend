package com.ElectronicStore.ElectronicStore.DTO;


import lombok.*;
import org.springframework.http.HttpStatus;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class APiResponseMessage {

    private String message;
    private boolean success;
    private HttpStatus status;
    private String navigate;
}
