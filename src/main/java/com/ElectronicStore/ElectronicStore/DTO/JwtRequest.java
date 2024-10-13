package com.ElectronicStore.ElectronicStore.DTO;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtRequest {
 private String email;
 private String password;
}
