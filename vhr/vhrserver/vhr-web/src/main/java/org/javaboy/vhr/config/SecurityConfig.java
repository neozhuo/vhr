package org.javaboy.vhr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**","/css/**","/img/**","/fonts/**","/verifyCode","/favicon.ico","/index.html");
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin().loginPage("/index.html");
//    }
}
