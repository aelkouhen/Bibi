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
        /*
        http.csrf().disable()
                .logout().logoutSuccessUrl("/").and()
                .authorizeRequests()
                .antMatchers("/index.html", "/").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll();
         */
        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**")
                .permitAll()
                .anyRequest()
                .authenticated();

        // @formatter:on
    }
}
