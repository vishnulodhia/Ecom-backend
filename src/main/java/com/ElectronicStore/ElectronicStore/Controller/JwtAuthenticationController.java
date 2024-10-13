package com.ElectronicStore.ElectronicStore.Controller;


import ch.qos.logback.classic.Logger;
import com.ElectronicStore.ElectronicStore.DTO.JwtRequest;
import com.ElectronicStore.ElectronicStore.DTO.JwtResponse;
import com.ElectronicStore.ElectronicStore.DTO.UserDto;
import com.ElectronicStore.ElectronicStore.Model.User;
import com.ElectronicStore.ElectronicStore.Security.JwtHelper;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ModelMapper mapper;


    private Logger logger = (Logger) LoggerFactory.getLogger(JwtAuthenticationController.class);

    @PostMapping(value= "/generate-token")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
   logger.info("username: ",request.getEmail(),"Password",request.getPassword());

        this.doAuthenticate(request.getEmail(),request.getPassword());
        User user = (User)userDetailsService.loadUserByUsername(request.getEmail());
       String token = jwtHelper.generateToken(user);

       return new ResponseEntity<>(JwtResponse.builder().token(token).user(mapper.map(user, UserDto.class)).build(), HttpStatus.OK);
    }

    private void doAuthenticate(String email,String password){
        try{

            Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);
            authenticationManager.authenticate(authentication);
        }
        catch (BadCredentialsException e){
            System.out.println("Invalid user exceptions");
           throw new BadCredentialsException("Invalid username and password !!");
        }

    }

}
