package com.ElectronicStore.ElectronicStore.Controller;
import com.ElectronicStore.ElectronicStore.DTO.APiResponseMessage;
import com.ElectronicStore.ElectronicStore.DTO.UserDto;
import com.ElectronicStore.ElectronicStore.Service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RequestMapping("/otp")
@RestController
public class OTPController {
    @Autowired
    private OTPService otpService;

    @PostMapping("/generate-otp")
    public ResponseEntity<APiResponseMessage> generateOTP(@RequestBody Map<String, String> body) {
       try {
           String email = body.get("email");
           System.out.println("Body: "+body);
           APiResponseMessage message =APiResponseMessage.builder().success(true).message(otpService.generateOtpAndSend(email)).status(HttpStatus.OK).build();
          return new ResponseEntity<>(message, HttpStatus.OK);
        }
       catch (Exception e){

           APiResponseMessage message =APiResponseMessage.builder().success(false).message(e.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
           return new ResponseEntity<>(message,HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<APiResponseMessage> validateOTP(@RequestParam String otp, @RequestBody UserDto userDto) {
      try{
          APiResponseMessage message =APiResponseMessage.builder().success(true).message(otpService.validOtpEmail(userDto,otp)).status(HttpStatus.OK).build();
          return new ResponseEntity<>(message,HttpStatus.OK);
      }
      catch (Exception e){
          APiResponseMessage message =APiResponseMessage.builder().success(false).message(e.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
          return new ResponseEntity<>(message,HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
}
