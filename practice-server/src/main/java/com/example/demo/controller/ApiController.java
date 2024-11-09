package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins={"http://localhost:5173", "http://junhee92kr.com"})
@RequestMapping("/api") 
public class ApiController {
	protected void show() {}
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@GetMapping("/")
	public String init() {		
		return "안녕하세요";
	}
	
	@GetMapping("/girlfriend/love")
	public String showLoveToGirlFriend() {
		return "I Love you♥";
	}
	
	@GetMapping("/country/code/{code}")
	public String printCodeInfo(@PathVariable int code) {
		COUNTRY country = COUNTRY.AMERICA;		
		return String.format("국가코드: %d", country.code);
	}
	
	@GetMapping("/update/cache")
	public String updateCacheData() {
		redisTemplate.opsForValue().set("name", "junhee");
		redisTemplate.expire("name", 1, TimeUnit.DAYS);
		return "";
	}
	
	@GetMapping("/get/cache")
	public String getCacheData() {
		String result = redisTemplate.opsForValue().get("name");
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/sort/user-list")
	public void showSortedUserList() {
		List<User> userList = new ArrayList<User>();
		userList.add(new User(33, "junhee"));
		userList.add(new User(30, "inhee"));		
		Collections.sort(userList);
		Consumer<User> method = (user) -> System.out.println(user.getAge());
		userList.forEach(method);
	}
	
	interface StringFunction {
		String getResult(String str);
	}
	
	public class Sortuser implements Comparator<User> {
		@Override
		public int compare(User u1, User u2) {
			if (u1.getAge() < u2.getAge()) return -1;
			if (u1.getAge() > u2.getAge()) return 1;
			return 0;
		}		
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