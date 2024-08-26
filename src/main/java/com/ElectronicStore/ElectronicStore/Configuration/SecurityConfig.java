package com.ElectronicStore.ElectronicStore.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.PasswordManagementDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig {

    @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // CSRF protection is disabled, consider enabling it in production
                .cors(cors -> cors.disable()) // CORS is disabled, enable and configure it if needed
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.DELETE, "/user/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/user/**").hasAnyRole("ADMIN", "NORMAL")
                        .requestMatchers(HttpMethod.POST, "/Product/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/Product/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/Product/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/Order/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/Category/**").hasRole("ADMIN")

                        // Normal user-specific routes
                        .requestMatchers("/Order/**").hasRole("NORMAL")
                        .requestMatchers("/Cart/**").hasRole("NORMAL")

                        // Publicly accessible routes
                        .requestMatchers(HttpMethod.POST, "/user/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/Product/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/Category/**").permitAll()
                        .requestMatchers("/otp/**").permitAll()

                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

          System.out.println("HTTPS:"+ http);
          return http.build();

    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails user = User.builder()
//                .username("Vishnu")
//                .password(passwordEncoder().encode("Windows@8"))
//                .roles("Normal")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("Admin")
//                .password(passwordEncoder().encode("Admin@8"))
//                .roles("Admin")
//                .build();
//
//        return new InMemoryUserDetailsManager(user,admin);
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}
