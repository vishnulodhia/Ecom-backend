package com.ElectronicStore.ElectronicStore.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    public void sendOtpEmail(String email, String otp){
       System.out.print("email"+email);
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        ZonedDateTime fiveMinutesLater = zonedDateTime.plusMinutes(5);
        String formattedTime = fiveMinutesLater.format(DateTimeFormatter.ofPattern("HH:mm"));;

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Verify OTP");
        simpleMailMessage.setText("Your OTP for registration is: " + otp + " It will be valid upto " + formattedTime);

        javaMailSender.send(simpleMailMessage);
        System.out.println("OTP sent to: " + email);
    }

}
