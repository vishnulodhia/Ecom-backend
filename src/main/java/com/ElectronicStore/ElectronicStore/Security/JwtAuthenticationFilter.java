package com.ElectronicStore.ElectronicStore.Security;


import ch.qos.logback.classic.Logger;
import com.ElectronicStore.ElectronicStore.Service.CustomUserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private Logger logger = (Logger) LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if(requestHeader !=null && requestHeader.startsWith("Bearer")){
            token = requestHeader.substring(7);
           try{
               username = jwtHelper.getUsernameFromToken(token);
               logger.info("Token username:",username);
           }
           catch (IllegalArgumentException e) {
               logger.info("Illegal Argument while fetching the username !!");
               e.printStackTrace();
           } catch (ExpiredJwtException e) {
               logger.info("Given jwt token is expired !!");
               e.printStackTrace();
           } catch (MalformedJwtException e) {
               logger.info("Some changed has done in token !! Invalid Token");
               e.printStackTrace();
           } catch (Exception e) {
               e.printStackTrace();
           }
        }
        else{
            logger.info("Invalid Header header is not starting with bear");
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(username.equals(userDetails.getUsername()) && jwtHelper.isTokenExpired(token)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
         filterChain.doFilter(request,response);
    }
}
