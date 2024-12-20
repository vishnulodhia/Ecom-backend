package com.ElectronicStore.ElectronicStore.Configuration;

import com.ElectronicStore.ElectronicStore.Model.User;
import com.ElectronicStore.ElectronicStore.Security.JwtAuthenticationEntryPoint;
import com.ElectronicStore.ElectronicStore.Security.JwtAuthenticationFilter;
import com.ElectronicStore.ElectronicStore.Service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.PasswordManagementDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter filter;

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Autowired
    private UserDetailsService userDetailService;

    @Autowired
    private  CustomUserDetailService customUserDetailService;


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
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .requestMatchers("/otp/**").permitAll()

                );
                 http.exceptionHandling(ex->ex.authenticationEntryPoint(entryPoint));
                 http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                 http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

          System.out.println("HTTPS:"+ http);
          return http.build();

    }



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }



}
