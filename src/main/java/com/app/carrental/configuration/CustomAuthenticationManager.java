package com.app.carrental.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Autowired
    CustomAuthenticationFailureHandler failureHandler;

    private final HttpServletResponse response;
    private final HttpServletRequest request;

    public CustomAuthenticationManager(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
    }

    @Override
    public Authentication authenticate(Authentication authentication){
        Authentication auth = null;
        if (authentication.getCredentials().toString().equals("user") && authentication.getName().equals("user")){
            auth = new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials().toString(), List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
            successHandler.onAuthenticationSuccess(request, response, authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else{
            failureHandler.onAuthenticationFailure(request, response, new BadCredentialsException("Invalid login details."));
        }
        return auth;
    }
}
