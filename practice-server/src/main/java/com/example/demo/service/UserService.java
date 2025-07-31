package com.example.demo.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.code.ErrorCode;
import com.example.demo.common.exception.BaseException;
import com.example.demo.dto.token.TokenDto;
import com.example.demo.dto.user.UserCreateRequestDto;
import com.example.demo.dto.user.UserCreateResponseDto;
import com.example.demo.dto.user.UserLoginRequestDto;
import com.example.demo.dto.user.UserResponseDto;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.JwtTokenProvider;
import com.example.demo.utils.PasswordUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	public List<UserResponseDto> getUserList() {
		List<User> userList = userRepository.findAll();
		return userList.stream().map(UserMapper.INSTANCE::userToUserResponse).collect(Collectors.toList());
	}
	
	public UserResponseDto getUserById(Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		User user = userOptional.isPresent() ? userOptional.get() : null; 
		return UserMapper.INSTANCE.userToUserResponse(user);
	}
	
	public UserResponseDto getUserByEmail(String email) {
		Optional<User> userOptional = userRepository.findByEmail(email);
		User user = userOptional.isPresent() ? userOptional.get() : null;
		return UserMapper.INSTANCE.userToUserResponse(user);
	}
	
	@Transactional
	public UserCreateResponseDto saveUser(UserCreateRequestDto userRequest) {
		UserResponseDto userResposne = this.getUserByEmail(userRequest.getEmail());
		if (userResposne != null) {
			throw new BaseException(ErrorCode.USER_DUPLICATED);
		}
		String encodedPassword = PasswordUtils.encodePassword(userRequest.getPassword());
		userRequest.setPassword(encodedPassword);
		User user = UserMapper.INSTANCE.userCreateRequestToUser(userRequest);
		UserCreateResponseDto userCreateResponse = UserMapper.INSTANCE.userCreateResponseDto(user);
		userRepository.save(user);
		return userCreateResponse;
	}
	
	@Transactional
	public TokenDto login(UserLoginRequestDto loginRequest) {
		Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
		User user = userOptional.isPresent() ? userOptional.get() : null;
		if (user == null) {
			throw new BaseException(ErrorCode.USER_NOT_FOUND);
		}
		boolean matched = PasswordUtils.matches(loginRequest.getPassword(), user.getPassword()); 
		if (!matched) {
			throw new BaseException(ErrorCode.USER_PASSWORD_INCORRECT);
		}
		// 로그인 검증이 통과하면 토큰 반환
		// refreshToken DB 저장
		Long userId = user.getId();
		String strUserId = userId.toString();
		LocalDateTime expiryDate = LocalDateTime.now().plusDays(7);
		
	    String accessToken = jwtTokenProvider.createAccessToken(strUserId);
	    String refreshToken = jwtTokenProvider.createRefreshToken(strUserId);
	    
	    tokenService.saveRefreshToken(userId, refreshToken, expiryDate);
	    
	    return new TokenDto(accessToken, refreshToken, expiryDate);	
	}
	
	public String getCookieValue(HttpServletRequest httpRequest, TokenDto tokenResponseDto) {
		Instant instant = tokenResponseDto.getExpiryDate().atZone(ZoneId.systemDefault()).toInstant();
		long cookieMaxAge = instant.getEpochSecond() - Instant.now().getEpochSecond();
		
		String origin = httpRequest.getHeader("Origin");
	    boolean isLocal = origin != null && origin.contains("localhost");
	    String cookieValue;
		
	    if (isLocal) {
	    	// Secure 제거
	        cookieValue = String.format("access-token=%s; Max-Age=%d; Path=/; SameSite=Lax", tokenResponseDto.getAccessToken(), cookieMaxAge);
	    } else {
	        cookieValue = String.format("access-token=%s; Max-Age=%d; Path=/; Domain=junhee92kr.com; SameSite=None; Secure; HttpOnly", tokenResponseDto.getAccessToken(), cookieMaxAge);
	    }
	    
	    log.info("[getCookieValue]");
	    log.info("origin: {}", origin);
	    log.info("cookieValue: {}", cookieValue);
	    
		return cookieValue;
	}

}
