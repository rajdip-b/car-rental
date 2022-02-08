package com.app.carrental.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationManager authenticationManager;

    @Autowired
    private CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
//        http.authorizeRequests().antMatchers("/index2").hasAnyRole("ROLE_ADMIN");
//        http.authorizeRequests().antMatchers("/index2").authenticated();
//        http.authorizeRequests().antMatchers("/login*").permitAll();
//        http.sessionManagement().maximumSessions(1);
        http.authorizeRequests().antMatchers("/**").permitAll();
    }

}
