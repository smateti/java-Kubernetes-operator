package gov.nystax.nimbus.react;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.Map;

@Configuration
public class WebSocketConfig {

    @Bean
    public HandlerMapping webSocketMapping(CustomWebSocketHandler webSocketHandler) {
        return new SimpleUrlHandlerMapping(Map.of(
                "/ws/echo", webSocketHandler // Map the endpoint to the handler
        ), 1); // Order (lower number means higher priority)
    }

    @Bean
    public WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
