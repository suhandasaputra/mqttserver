//package com.example.mqttserver.interceptor;
//
//import io.moquette.interception.AbstractInterceptHandler;
//import io.moquette.interception.messages.InterceptPublishMessage;
//import io.netty.buffer.ByteBuf;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.nio.charset.StandardCharsets;
//
//@Component
//public class PublishInterceptor extends AbstractInterceptHandler {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(PublishInterceptor.class);
//
//    @Override
//    public String getID() {
//        return "PublishInterceptor";
//    }
//
//    @Override
//    public void onPublish(InterceptPublishMessage msg) {
//        ByteBuf payload = msg.getPayload();
//        byte[] byteArray = new byte[payload.readableBytes()];
//        payload.getBytes(payload.readerIndex(), byteArray);
//        String payloadString = new String(byteArray, StandardCharsets.UTF_8);
//
//        LOGGER.info("Received message on topic {}: {}", msg.getTopicName(), payloadString);
//    }
//
//    @Override
//    public Class<?>[] getInterceptedMessageTypes() {
//        return new Class<?>[]{InterceptPublishMessage.class};
//    }
//}



//package com.example.mqttserver.interceptor;
//
//import com.example.mqttserver.entity.ClientConnection;
//import com.example.mqttserver.repository.ClientConnectionRepository;
//import io.moquette.interception.AbstractInterceptHandler;
//import io.moquette.interception.messages.InterceptPublishMessage;
//import io.netty.buffer.ByteBuf;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDateTime;
//
//@Component
//public class PublishInterceptor extends AbstractInterceptHandler {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(PublishInterceptor.class);
//
//    @Autowired
//    private ClientConnectionRepository clientConnectionRepository;
//
//    @Override
//    public String getID() {
//        return "PublishInterceptor";
//    }
//
//    @Override
//    public void onPublish(InterceptPublishMessage msg) {
//        ByteBuf payload = msg.getPayload();
//        byte[] byteArray = new byte[payload.readableBytes()];
//        payload.getBytes(payload.readerIndex(), byteArray);
//        String payloadString = new String(byteArray, StandardCharsets.UTF_8);
//
//        // Log the received message
//        LOGGER.info("Received message on topic {}: {}", msg.getTopicName(), payloadString);
//
//        // Create a new ClientConnection entity
//        ClientConnection clientConnection = new ClientConnection();
//        clientConnection.setClientId("ExampleClientID"); // Ganti dengan ID client yang sesuai jika tersedia
//        clientConnection.setUsername("ExampleUsername"); // Ganti dengan username yang sesuai jika tersedia
//        clientConnection.setEvent("Message Received");
//        clientConnection.setEventTime(LocalDateTime.now());
//        clientConnection.setMessageContent(payloadString);
//
//        // Save the entity to the database
//        clientConnectionRepository.save(clientConnection);
//    }
//
//    @Override
//    public Class<?>[] getInterceptedMessageTypes() {
//        return new Class<?>[]{InterceptPublishMessage.class};
//    }
//}


package com.example.mqttserver.interceptor;

import com.example.mqttserver.entity.ClientConnection;
import com.example.mqttserver.repository.ClientConnectionRepository;
import io.moquette.interception.AbstractInterceptHandler;
import io.moquette.interception.messages.InterceptPublishMessage;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
public class PublishInterceptor extends AbstractInterceptHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublishInterceptor.class);

    @Autowired
    private ClientConnectionRepository clientConnectionRepository;

    @Override
    public String getID() {
        return "PublishInterceptor";
    }

    @Override
    public void onPublish(InterceptPublishMessage msg) {
        ByteBuf payload = msg.getPayload();
        byte[] byteArray = new byte[payload.readableBytes()];
        payload.getBytes(payload.readerIndex(), byteArray);
        String payloadString = new String(byteArray, StandardCharsets.UTF_8);

        // Get client information from ConnectionInterceptor
//        String username = ConnectionInterceptor.getUsernameForClientId(msg.getClientID());

        // Log the received message
        LOGGER.info("Received message on topic {}: {}", msg.getTopicName(), payloadString);

        // Create a new ClientConnection entity
        ClientConnection clientConnection = new ClientConnection();
        clientConnection.setClientId(msg.getClientID());
        clientConnection.setUsername(msg.getUsername());
        clientConnection.setEvent("Message Received");
        clientConnection.setEventTime(LocalDateTime.now());
        clientConnection.setMessageContent(payloadString);

        // Save the entity to the database
        clientConnectionRepository.save(clientConnection);
    }

    @Override
    public Class<?>[] getInterceptedMessageTypes() {
        return new Class<?>[]{InterceptPublishMessage.class};
    }
}
