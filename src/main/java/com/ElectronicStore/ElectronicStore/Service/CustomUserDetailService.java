package com.ElectronicStore.ElectronicStore.Service;

import com.ElectronicStore.ElectronicStore.Exception.ResourceNotFoundException;
import com.ElectronicStore.ElectronicStore.Model.User;
import com.ElectronicStore.ElectronicStore.Repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class CustomUserDetailService implements UserDetailsService {


    @Autowired
    private UserRepositories userRepositories;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Log the username for debugging purposes
        System.out.println("THIS IS WORKING :::  " + username);

            User  user = userRepositories.findByEmail(username)
                      .orElseThrow(() -> new ResourceNotFoundException("User not found"));
              System.out.println("User:" + user.getAuthorities());

             return user;
    }

}



