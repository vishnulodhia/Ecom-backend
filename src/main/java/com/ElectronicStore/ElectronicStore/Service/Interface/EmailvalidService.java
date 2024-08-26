package com.ElectronicStore.ElectronicStore.Service.Interface;

import com.ElectronicStore.ElectronicStore.DTO.UserDto;

public interface EmailvalidService {

    public String generateOtpAndSend(String email) ;

    public String validOtpEmail(UserDto userDto, String otp);

}
