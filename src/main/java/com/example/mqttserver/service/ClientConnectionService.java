/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.mqttserver.service;

/**
 *
 * @author suhan
 */

import com.example.mqttserver.entity.ClientConnection;
import com.example.mqttserver.repository.ClientConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ClientConnectionService {

    @Autowired
    private ClientConnectionRepository clientConnectionRepository;

    public void saveClientConnection(String clientId, String username, String event) {
        ClientConnection clientConnection = new ClientConnection();
        clientConnection.setClientId(clientId);
        clientConnection.setUsername(username);
        clientConnection.setEvent(event);
        clientConnection.setEventTime(LocalDateTime.now());
        clientConnectionRepository.save(clientConnection);
    }
}
