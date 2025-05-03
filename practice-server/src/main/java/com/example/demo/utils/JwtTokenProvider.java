package com.example.demo.utils;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	@Value("${jwt.secret-key}")
	private String secretKey;
	
	@Value("${jwt.access-token-valid-time}")
    private long accessTokenValidTime;
	
	@Value("${jwt.refresh-token-valid-time}")
    private long refreshTokenValidTime;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(String userId) {
        return createToken(userId, accessTokenValidTime);
    }

    public String createRefreshToken(String userId) {
        return createToken(userId, refreshTokenValidTime);
    }

    private String createToken(String userId, long tokenValidTime) {
        Claims claims = Jwts.claims().setSubject(userId);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(this.getSigningKey())
                .compact();
    }

    public String getUserId(String token) {
        return Jwts.parserBuilder()
        		.setSigningKey(this.getSigningKey())
        		.build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(this.getSigningKey())
            .build()
            .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
