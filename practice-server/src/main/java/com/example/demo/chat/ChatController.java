package com.example.demo.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
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
@CrossOrigin(origins= {"http://localhost:5173", "http://localhost:3000", "https://junhee92kr.com"})
public class ChatController {
	private static final String REDIS_CHAT_KEY = "chat";
	@Autowired
	private RedisTemplate<String, List<ChatMessage>> redisTemplate;

    @MessageMapping("/send")
    @SendTo("/topic/greetings")
    public List<ChatMessage> handleHelloMessage(@Payload ChatMessage chatMessage) throws JsonMappingException, JsonProcessingException {
    	String content = chatMessage.getContent();
    	boolean isContentEmpty = content == null || content.trim().isEmpty();
    	List<ChatMessage> messages = redisTemplate.opsForValue().get(REDIS_CHAT_KEY);
    	
    	if (messages == null) {
            messages = new ArrayList<>();
            if (isContentEmpty) {
            	return messages;
            }
        }
    	if (!isContentEmpty) {
    		// 메시지 추가 및 10개 유지
    		if (messages.size() >= 20) {
            	messages.remove(0); // 오래된 것 제거
        	}        
        	messages.add(chatMessage);
        	redisTemplate.opsForValue().set(REDIS_CHAT_KEY, messages, 1, TimeUnit.DAYS); // Redis에 다시 저장
        }

        return messages;
    }
}

