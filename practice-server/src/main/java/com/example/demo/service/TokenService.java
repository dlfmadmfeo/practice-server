package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.RefreshToken;
import com.example.demo.repository.RefreshTokenRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
	@Autowired
    private RefreshTokenRepository refreshTokenRepository;

	@Transactional
    public void saveRefreshToken(Long userId, String refreshToken, LocalDateTime expiryDate) {
    	RefreshToken token = refreshTokenRepository.findByUserId(userId).orElse(new RefreshToken()); 
    	token.setUserId(userId);
    	token.setToken(refreshToken);
    	token.setExpireDate(expiryDate);    	
        refreshTokenRepository.save(token);
    }

    public boolean isRefreshTokenExist(Long userId, String refreshToken) {
    	Optional<RefreshToken> optionalToken = refreshTokenRepository.findByUserId(userId);
    	RefreshToken token = optionalToken.isPresent() ? optionalToken.get() : null;
    	return token != null;
    }
}
