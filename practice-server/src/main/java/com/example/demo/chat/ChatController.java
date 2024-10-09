package com.example.demo.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@CrossOrigin(origins= {"http://localhost:5173", "https://junhee92kr.com"})
public class ChatController {
	private final ObjectMapper objectMapper = new ObjectMapper();
	private List<String> messages = new ArrayList<String>();
	private static final String REDIS_CHAT_KEY = "chat";
	@Autowired
	private RedisTemplate<String, List<String>> redisTemplate;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String[] handleHelloMessage(String message) throws JsonMappingException, JsonProcessingException {
//    	redisTemplate.opsForValue().set(REDIS_CHAT_KEY, null);    	
//    	redisTemplate.expire(REDIS_CHAT_KEY, 1, TimeUnit.DAYS);
    	
//    	List<String> redisMessages = redisTemplate.opsForValue().get(REDIS_CHAT_KEY);
//    	System.out.println("redisMessages: " + redisMessages);
    	
    	JsonNode jsonNode = objectMapper.readTree(message);
    	String content = jsonNode.get("content").asText();
    	if (!content.isEmpty()) {
	    	if (messages.size() >= 100) {
	    		messages.clear();
	    	}
    		messages.add(content);
    	}
    	
    	
    	return messages.toArray(new String[0]);
    }
}

