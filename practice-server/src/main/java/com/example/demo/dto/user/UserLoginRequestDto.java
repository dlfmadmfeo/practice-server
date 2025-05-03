package com.example.demo.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
//@ToString
public class UserLoginRequestDto {
	private String password;
	private String email;
}
