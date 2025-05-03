package com.example.demo.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	SUCCESS("COMMON.0000", "성공"),
	
    SERVER_ERROR("SERVER.5000", "서버 오류 발생"),
    SERVER_CONNECTION_FAILURE("SERVER.5001", "서버 연결 실패"),
    SERVER_TIMEOUT("SERVER.5002", "서버 타임아웃"),
    DUPLICATED_DATA("SERVER.5003", "중복된 데이터가 존재합니다."),
    
    USER_NOT_FOUND("USER.4040", "사용자가 존재하지 않습니다."),
    USER_AUTH_FAILED("USER.4041", "사용자 인증 실패"),
    USER_DUPLICATED("USER.4042", "중복된 사용자가 존재합니다."),
    USER_PASSWORD_INCORRECT("USER.4043", "비밀번호가 일치하지 않습니다."),
    
    TOKEN_INVALID("TOKEN.0001", "토큰 정보가 올바르지 않습니다."),
    
    DATABASE_CONNECTION_FAILURE("DB.5001", "데이터베이스 연결 실패");
	
	private final String code;
	private final String message;
}
