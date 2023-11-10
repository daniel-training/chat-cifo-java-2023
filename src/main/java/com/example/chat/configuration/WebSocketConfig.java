package com.example.chat.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configuration class that prepares Spring to enable WebSocket and STOMP messaging. The message handling are backed by
 * a message broker. The class implements <code>WebSocketMessageBrokerConfigurer</code>. Overrides
 * <code>registerStompEndpoints()</code> to set the endpoint to Register/Connect to the Chat WebSocket service
 * (handshake). And Overrides <code>configureMessageBroker</code> to configure the Endpoints to consume the Chat
 * services.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Configures the message Broker
     * <p>
     * To subscriptions, configures prefixes Endpoints to consume the Chat services. For example, a client app uses
     * following endpoints:
     * <p>
     * Publish/Subscribe (broadcast to multiple consumers)
     * <p>
     * <pre>{@code /topic/{room} }</pre>
     * <p>
     * Point to Point (unique consumer) TODO: pending to implement.
     * <p>
     * <pre>{@code /queue/{user} }</pre>
     * <p>
     * To a client send messages to the server, configures the destination endpoint prefix. For example: Send message
     * <pre>{@code /app/chat/{room} }</pre>
     *
     * @param config registry for configuring message broker options.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * Register and configures STOMP endpoints mappings.
     * <p>
     * A client Registers/Connects to the Chat WebSocket service (handshake) using following endpoint:
     * <pre>{@code
     * /chat-websocket-service
     * }</pre>
     * Examples:
     * <pre>{@code ws://exampledomain.com/chat-websocket-service }</pre>
     * <pre>{@code ws://localhost:8080/chat-websocket-service }</pre>
     * (*) For the handshake the ws protocol is used to request the WebSocket connection.
     *
     * @param registry used for registering STOMP over WebSocket endpoints by providing the methods to make the
     *                 configuration.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat-websocket-service").setAllowedOriginPatterns("*");
    }

}
