package com.example.mqttserver.service;

import io.moquette.broker.Server;
import io.moquette.broker.config.IConfig;
import io.moquette.broker.config.MemoryConfig;
import io.moquette.interception.InterceptHandler;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Properties;

@Service
public class MqttBrokerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqttBrokerService.class);

    private Server mqttBroker;

    @Autowired
    private List<InterceptHandler> interceptHandlers;

    @PostConstruct
    public void startBroker() {
        try {
            LOGGER.info("Starting MQTT Broker...");
            mqttBroker = new Server();
            Properties configProps = new Properties();
            configProps.put("port", "1883");
            IConfig config = new MemoryConfig(configProps);
            mqttBroker.startServer(config, interceptHandlers);
            LOGGER.info("MQTT Broker started successfully.");
        } catch (IOException e) {
            LOGGER.error("Error starting MQTT Broker: ", e);
        }
    }

    @PreDestroy
    public void stopBroker() {
        if (mqttBroker != null) {
            LOGGER.info("Stopping MQTT Broker...");
            mqttBroker.stopServer();
            LOGGER.info("MQTT Broker stopped successfully.");
        }
    }

    public Server getMqttBroker() {
        return mqttBroker;
    }
}
