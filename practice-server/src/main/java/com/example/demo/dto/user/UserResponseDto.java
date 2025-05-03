package com.example.demo.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserResponseDto {
	private Long id;
	private String name;
	private String email;
	private String phone;
	private String regDate;
	private String modDate;
}
