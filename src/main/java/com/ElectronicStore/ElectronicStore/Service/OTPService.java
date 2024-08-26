package com.ElectronicStore.ElectronicStore.Service;


import com.ElectronicStore.ElectronicStore.DTO.OTPDto;
import com.ElectronicStore.ElectronicStore.DTO.UserDto;
import com.ElectronicStore.ElectronicStore.Model.OTP;
import com.ElectronicStore.ElectronicStore.Model.User;
import com.ElectronicStore.ElectronicStore.Repositories.OtpRepositories;
import com.ElectronicStore.ElectronicStore.Repositories.UserRepositories;
import com.ElectronicStore.ElectronicStore.Service.Interface.EmailvalidService;
import com.ElectronicStore.ElectronicStore.Service.Interface.UserService;
import com.ElectronicStore.ElectronicStore.util.EmailUtil;
import com.ElectronicStore.ElectronicStore.util.OTPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class OTPService implements EmailvalidService {

    @Autowired
    EmailUtil emailUtil;

    @Autowired
    OTPUtil otpUtil;

    @Autowired
    OtpRepositories otpRepositories;

    @Autowired
    UserRepositories userRepositories;

    @Autowired
    UserService userService;

    @Override
    public String generateOtpAndSend(String email)  {
        String generatedOtp = otpUtil.generateOtp();
        emailUtil.sendOtpEmail(email,generatedOtp);

        OTP  otp = new OTP();
        otp.setOtp(generatedOtp);
        otp.setEmail(email);
        otpRepositories.save(otp);
        return "OTP sent To Your Email";
    }

    @Override
    public String validOtpEmail(UserDto userDto, String otp) {

        OTP ot= otpRepositories.findByOtpAndEmail(otp,userDto.getEmail());

        if(ot == null){
            if(otpRepositories.existsByotp(otp))
                return "Fail to register due to invalid email";
            return "Fail to register due to invalid OTP";
        }

        OTPDto otpDto = entityToDto(ot);

         if( isTimeNotExpired(otpDto.getOtpGeneratedTime())){
             System.out.println("Userdto:"+userDto);
           userService.createUser(userDto);
            return "Account successfully created!";
        }


        return  "Fail to register due to otp Time Expire";


    }

    public static boolean isTimeNotExpired (Timestamp ts1) {

        ZonedDateTime nowInKolkata = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        Timestamp currentTimestamp = Timestamp.from(nowInKolkata.toInstant());
       System.out.println("Currentime:"+currentTimestamp+" ts1:"+ts1);
       ts1 = new Timestamp(ts1.getTime() + (5 * 60 * 1000));
        return currentTimestamp.before(ts1);
    }

//For OTP
    OTPDto entityToDto(OTP otp){

        OTPDto otpdto = OTPDto.builder()
                .otpId(otp.getOtpId())
                .otp(otp.getOtp())
                .otpGeneratedTime(otp.getOtpGeneratedTime())
                .build();
        return otpdto;

    }





}





