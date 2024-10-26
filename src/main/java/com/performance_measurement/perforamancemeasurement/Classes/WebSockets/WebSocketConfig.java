package com.performance_measurement.perforamancemeasurement.Classes.WebSockets;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// indicate that it is a Spring configuration class
@Configuration
// @EnableWebSocketMessageBroker enables WebSocket message handling, backed by a message broker.
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // For example, /app/hello is the endpoint that the GreetingController.greeting() method is mapped to handle.
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // enable a simple memory-based message broker to carry the greeting messages back to the client on destinations prefixed with /topic
        config.enableSimpleBroker("/topic");
        // designates the /app prefix for messages that are bound for methods annotated with @MessageMapping
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket");
    }

}