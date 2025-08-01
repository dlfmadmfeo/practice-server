package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.service.UserService;
import com.example.demo.utils.JwtTokenProvider;

@Configuration
public class SecurityConfig {
	
	private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
	
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

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
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()
            )
            .cors(cors -> {}) // Spring의 WebMvcConfigurer에서 설정한 CORS 정책을 적용해줌
            .csrf(csrf -> csrf.disable()) // 비활성화 필요 시만 사용
            .httpBasic(httpBasic -> httpBasic.disable())
            .formLogin(form -> form.disable())
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, userService), UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}