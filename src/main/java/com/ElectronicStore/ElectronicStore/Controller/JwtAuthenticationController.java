package com.ElectronicStore.ElectronicStore.Controller;


import ch.qos.logback.classic.Logger;
import com.ElectronicStore.ElectronicStore.DTO.JwtRequest;
import com.ElectronicStore.ElectronicStore.DTO.JwtResponse;
import com.ElectronicStore.ElectronicStore.DTO.UserDto;
import com.ElectronicStore.ElectronicStore.Exception.BadApiRequest;
import com.ElectronicStore.ElectronicStore.Model.User;
import com.ElectronicStore.ElectronicStore.Security.JwtHelper;
import com.ElectronicStore.ElectronicStore.Service.CustomUserDetailService;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Security;

@RestController
@RequestMapping("/auth")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    private ModelMapper mapper;


    private Logger logger = (Logger) LoggerFactory.getLogger(JwtAuthenticationController.class);

    @PostMapping(value= "/generate-token")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) throws Exception {
   logger.info("username: ",request.getEmail(),"Password",request.getPassword());



       try{
           this.doAuthenticate(request.getEmail(),request.getPassword());
           UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
           String token = jwtHelper.generateToken((User) userDetails);
           return new ResponseEntity<>(JwtResponse.builder().token(token).user(mapper.map(userDetails, UserDto.class)).build(), HttpStatus.OK);
       }
       catch (Exception e){
           e.printStackTrace();
           throw new Exception("New problem");
       }

    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            throw new BadApiRequest(" Invalid Username or Password  !!");
        }

    }



}
