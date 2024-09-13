/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.mqttserver.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    public void publish(String topic, String messageContent) throws MqttException {
        MqttClient client = new MqttClient(brokerUrl, MqttClient.generateClientId(), new MemoryPersistence());
        client.connect();

        MqttMessage message = new MqttMessage(messageContent.getBytes());
        message.setQos(1);

        client.publish(topic, message);
        client.disconnect();
    }
}
