package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ApiController {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@GetMapping("/")
	public String init() {
		return "안녕 세계야.";
	}
	
	@GetMapping("/test")
	public String test() {
		return "just testing...!!";
	}
	
	@GetMapping("/country/code/{code}")
	public String printCodeInfo(@PathVariable int code) {
		COUNTRY country = COUNTRY.AMERICA;		
		return String.format("국가코드: %d", country.code);
	}
	
	@GetMapping("/update/cache")
	public String updateCacheData() {
		redisTemplate.opsForValue().set("name", "junhee");
		return "";
	}
	
	@GetMapping("/get/cache")
	public String getCacheData() {
		String result = redisTemplate.opsForValue().get("name");
		return result;
	}
}

enum COUNTRY {
	KOREA(1),
	AMERICA(2),
	JAPAN(3),
	CHINA(4);
	
	int code;

	COUNTRY(int code) {
		this.code = code;
	}
}