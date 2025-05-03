package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins={"http://localhost:5173", "http://localhost:3000", "http://junhee92kr.com"})
public class ApiController {
	@Autowired
	UserService userService;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@GetMapping("/")
	public String init() {		
		return "helloworld";
	}
}
