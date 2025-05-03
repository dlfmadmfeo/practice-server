package com.example.demo.common.response;

import com.example.demo.common.code.ErrorCode;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
	boolean success;
	T value;
	String code;
	String message;
	
    // 생성자, Getter/Setter 등 필요에 따라 추가
    public ApiResponse(boolean success, T value, String code, String message) {
        this.success = success;
        this.value = value;
        this.code = code;
        this.message = message;
    }

    public static <T> ApiResponse<T> success(T value) {
        return new ApiResponse<>(true, value, ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage());
    }

    public static <T> ApiResponse<T> fail(String code, String message) {
        return new ApiResponse<>(false, null, code, message);
    }
}
