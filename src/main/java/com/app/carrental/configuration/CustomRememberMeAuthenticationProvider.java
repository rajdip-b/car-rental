package com.app.carrental.configuration;

import org.springframework.security.authentication.RememberMeAuthenticationProvider;

public class CustomRememberMeAuthenticationProvider extends RememberMeAuthenticationProvider {
    public CustomRememberMeAuthenticationProvider(String key) {
        super(key);
    }
}
