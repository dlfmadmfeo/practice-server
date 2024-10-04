package com.example.demo.chat;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
public class WebSocketTokenFilter implements ChannelInterceptor {
//	private final JWTUtils jwtUtils;
//  private final UserDetailsServiceImpl userDetailsService;
	  
	@Override
	public Message preSend(Message<?> message, MessageChannel channel) {
//		final StompHeaderAccessor stompHeaderAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//		stompHeaderAccessor.setUser(null);
//		if (stompHeaderAccessor.getCommand() == StompCommand.CONNECT) {
//		}
		
		return message;
	}
	
}
