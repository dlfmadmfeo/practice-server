package com.example.demo.controller;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.response.ApiResponse;
import com.example.demo.dto.token.TokenDto;
import com.example.demo.dto.user.UserCreateRequestDto;
import com.example.demo.dto.user.UserCreateResponseDto;
import com.example.demo.dto.user.UserLoginRequestDto;
import com.example.demo.dto.user.UserResponseDto;
import com.example.demo.service.AuthUser;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtTokenProvider;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin(origins={"http://localhost:5173", "http://localhost:3000", "http://junhee92kr.com"})
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
    private TokenService tokenService;
	
	@GetMapping(value="/list")
	public ApiResponse<List<UserResponseDto>> getUserList() {
		List<UserResponseDto> userList = userService.getUserList();
		return ApiResponse.success(userList);
	}
	
	@PostMapping
	public ApiResponse<UserCreateResponseDto> saveUser(@RequestBody UserCreateRequestDto user) {
		return ApiResponse.success(userService.saveUser(user));
	}
	
	@GetMapping("/{id}")
	public ApiResponse<UserResponseDto> getUser(@PathVariable Long id) {
		UserResponseDto user = userService.getUserById(id);
		return ApiResponse.success(user);
	}
	
	@PostMapping("/login")
	public ApiResponse<TokenDto> login(@RequestBody UserLoginRequestDto loginRequest, HttpServletResponse httpResponse) {
		log.info("loginRequest: {}", loginRequest);
		TokenDto tokenResponseDto = userService.login(loginRequest);
		
		Instant instant = tokenResponseDto.getExpiryDate().atZone(ZoneId.systemDefault()).toInstant();
		long cookieMaxAge = instant.getEpochSecond() - Instant.now().getEpochSecond();
		
		// 직접 Set-Cookie 헤더 설정
	    String cookieValue = String.format(
	        "access-token=%s; Max-Age=%d; Path=/; SameSite=None; HttpOnly",
	        tokenResponseDto.getAccessToken(),
	        cookieMaxAge
	    );
	    httpResponse.setHeader("Set-Cookie", cookieValue);
		
		return ApiResponse.success(tokenResponseDto);
	}
	
//    @PostMapping("/token/refresh")
//    public ApiResponse<TokenDto> refreshToken(@RequestBody String refreshToken) {
//        if (!jwtTokenProvider.validateToken(refreshToken)) {
//            throw new BaseException(ErrorCode.INVALID_REFRESH_TOKEN);
//        }
//
//        String userId = jwtTokenProvider.getUserId(refreshToken);
//
//        // DB에서 refreshToken 유효성 확인
//        if (!tokenService.isRefreshTokenValid(userId, refreshToken)) {
//            throw new BaseException(ErrorCode.INVALID_REFRESH_TOKEN);
//        }
//
//        String newAccessToken = jwtTokenProvider.createAccessToken(userId);
//        return ApiResponse.success(new TokenDto(newAccessToken, refreshToken));
//    }
	
//	@PutMapping("/user")
//	public User updateUser() {
//		return null;
//	}
}
