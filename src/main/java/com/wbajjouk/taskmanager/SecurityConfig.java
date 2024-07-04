package com.wbajjouk.taskmanager;

import com.wbajjouk.taskmanager.usermanagement.User;
import com.wbajjouk.taskmanager.usermanagement.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
@EnableWebSecurity
public class SecurityConfig {




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, DaoAuthenticationProvider daoAuthenticationProvider) throws Exception { // to define security rules for http request
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
////                                .antMatchers("/api/login").permitAll()
////                        .anyRequest().authenticated() // Ensures all requests are authenticated.
//                        )
                .httpBasic(Customizer.withDefaults())
                .authenticationProvider(daoAuthenticationProvider);
        return http.build();
    }

    @Bean
    @SessionScope
    public User user(){
        return new User();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}