package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                		"/**"
//                    "/swagger-ui/**",
//                    "/v3/api-docs/**",
//                    "/swagger-resources/**",
//                    "/webjars/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable()) // 비활성화 필요 시만 사용
            .httpBasic(httpBasic -> httpBasic.disable())
            .formLogin(form -> form.disable())
            .build();
    }
}