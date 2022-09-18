package com.example.test.config;

import com.example.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManagerBuilder auth) throws Exception {
        http.authorizeRequests()
                .antMatchers("/img/**", "/style/**", "/script/**", "/web/home", "/web/register",
                        "/main/registered/username/{username}/password/{password}/enabled/{enabled}/authority/{authority}",
                        "/web/save/username/{username}/authority/{authority}",
                        "/web/book/name/{name}/price/{price}/details/{details}/image/{image}", "/web/search/{name}",
                        "/web/details/{id}", "/web/problem", "/web/details/name/{name}", "/web/details/name1/{name1}",
                        "/web/details1/{id}")
                .permitAll()
                .antMatchers("/web/admin", "/web/admin/edit/{id}", "/web/admin/register",
                        "/web/best/edit/{name}", "/web/new/edit/{name}", "/web/blog/edit/{name}").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/web/login")
                .loginProcessingUrl("/login")
                .permitAll()
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/web/home")
                .permitAll();

        http.csrf().disable();

        auth.authenticationProvider(authenticationProvider());

        return http.build();
    }
}
