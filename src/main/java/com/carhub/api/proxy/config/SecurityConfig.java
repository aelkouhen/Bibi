package com.carhub.api.proxy.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableOAuth2Sso
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.csrf().disable()
                .logout().logoutSuccessUrl("/")
                .and()
                .authorizeRequests()
                .antMatchers("/login**", "/index" , "/")
                .permitAll()
                .anyRequest().authenticated();
        // @formatter:on
    }
}
