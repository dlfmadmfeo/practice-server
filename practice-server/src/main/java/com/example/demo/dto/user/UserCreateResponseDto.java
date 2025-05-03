package com.example.demo.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class UserCreateResponseDto {
	String name;
	String email;
	String phone;
}
