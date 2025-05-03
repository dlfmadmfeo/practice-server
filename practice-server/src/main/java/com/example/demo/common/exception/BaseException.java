package com.example.demo.common.exception;

import com.example.demo.common.code.ErrorCode;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
	private final ErrorCode errorCode;
	
	public BaseException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
