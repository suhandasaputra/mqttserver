package com.example.mqttserver.interceptor;

import com.example.mqttserver.entity.ClientConnection;
import com.example.mqttserver.repository.ClientConnectionRepository;
import io.moquette.interception.AbstractInterceptHandler;
import io.moquette.interception.messages.InterceptConnectMessage;
import io.moquette.interception.messages.InterceptDisconnectMessage;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConnectionInterceptor extends AbstractInterceptHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionInterceptor.class);

    @Autowired
    private ClientConnectionRepository clientConnectionRepository;

    // Map to store client information
    private static final ConcurrentHashMap<String, String> clientInfoMap = new ConcurrentHashMap<>();

    
    @Override
    public String getID() {
        return "ConnectionInterceptor";
    }

    @Override
    public void onConnect(InterceptConnectMessage msg) {
        LOGGER.info("Client connected: {}", msg.getClientID());
        LOGGER.info("Client keepalive: {}", msg.getKeepAlive());
        LOGGER.info("Client protocol name: {}", msg.getProtocolName());
        LOGGER.info("Client protocol version: {}", msg.getProtocolVersion());
        LOGGER.info("Client qos: {}", msg.getQos().toString());
        LOGGER.info("Client username: {}", msg.getUsername());
        
        

        // Optional: If password is available
        // LOGGER.info("Client password: {}", msg.getPassword());

        // Create and save a new ClientConnection entity
        ClientConnection clientConnection = new ClientConnection();
        clientConnection.setClientId(msg.getClientID());
        clientConnection.setUsername(msg.getUsername());
        clientConnection.setEvent("Client Connected");
        clientConnection.setEventTime(LocalDateTime.now());

        // Save the entity to the database
        clientConnectionRepository.save(clientConnection);
        
        // Store client info in map
        clientInfoMap.put(msg.getUsername(), msg.getUsername());
    }

    @Override
    public void onDisconnect(InterceptDisconnectMessage msg) {
        LOGGER.info("Client disconnected: {}", msg.getClientID());
        LOGGER.info("Client username: {}", msg.getUsername());

        // Create and save a new ClientConnection entity
        ClientConnection clientConnection = new ClientConnection();
        clientConnection.setClientId(msg.getClientID());
        clientConnection.setUsername(msg.getUsername());
        clientConnection.setEvent("Client Disconnected");
        clientConnection.setEventTime(LocalDateTime.now());

        // Save the entity to the database
        clientConnectionRepository.save(clientConnection);
        // Remove client info from map
        clientInfoMap.remove(msg.getClientID());
    }

    @Override
    public Class<?>[] getInterceptedMessageTypes() {
        return new Class<?>[]{InterceptConnectMessage.class, InterceptDisconnectMessage.class};
    }
    public static String getUsernameForClientId(String clientId) {
        return clientInfoMap.get(clientId);
    }
}
