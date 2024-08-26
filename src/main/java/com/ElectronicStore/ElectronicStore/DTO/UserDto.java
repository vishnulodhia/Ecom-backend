package com.ElectronicStore.ElectronicStore.DTO;


import com.ElectronicStore.ElectronicStore.Model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Collections;
import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class UserDto {
    private long userId;
    @Size(min=2, max=15, message = "Invalid Name !!")
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @Size(min = 4 , max = 8, message = "Invalid length password")
    @NotBlank
    private String password;
    @Size(min=10, max=10, message = "Invalid Phone No !!")
    @NotBlank
    private String phoneno;

//    private String role="NORMAL";
}
