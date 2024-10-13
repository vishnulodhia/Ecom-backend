package com.ElectronicStore.ElectronicStore.DTO;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
private String token;
private UserDto user;
private String refreshToken;
}
