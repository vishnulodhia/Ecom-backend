package com.ElectronicStore.ElectronicStore.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class OTPUtil {


    public String generateOtp(){
   Random random = new Random();
        System.out.println("Generating OTP using SecureRandom : ");
        System.out.print("Your OTP is : ");
        int length =6;
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom randomMethod = new SecureRandom();
        StringBuilder otp = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            otp.append(characters.charAt(randomMethod.nextInt(characters.length())));
        }

        return otp.toString();
    }


}
