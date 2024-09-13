/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.mqttserver.controller;

/**
 *
 * @author suhan
 */

import com.example.mqttserver.service.ClientConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {

    @Autowired
    private ClientConnectionService clientConnectionService;

    @PostMapping("/logConnection")
    public void logConnection(@RequestParam String clientId, @RequestParam String username, @RequestParam String event) {
        clientConnectionService.saveClientConnection(clientId, username, event);
    }
}
