package com.example.demo.chat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	private final WebSocketTokenFilter webSocketTokenFilter;
	
	public WebSocketConfig(WebSocketTokenFilter webSocketTokenFilter) {
		this.webSocketTokenFilter = webSocketTokenFilter;
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:5173", "https://junhee92kr.com", "http://junhee92kr.com", "https://junhee92kr.com.ngrok-free.app").withSockJS();
	}
	
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(webSocketTokenFilter);
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app") // 클라이언트->서버로 메시지 보낼 때 /app/something 으로 보내면 서버가 이 경로를 기준으로 메시지를 핸들러로 라우팅
			.enableSimpleBroker("/topic") // 해당 경로를 구독한 클라이언트들은 실시간으로 메시지 수신
			.setTaskScheduler(this.heartBeatScheduler()) // 웹소켓 연결 상태를 유지하기 위해 heartbeat 스케쥴러
			.setHeartbeatValue(new long[]{10000L, 10000L}); // heartbeat 주기(ms)
	}

    @Bean
    TaskScheduler heartBeatScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(1);
		scheduler.initialize();
		return scheduler;
	}
}
