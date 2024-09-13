/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.mqttserver.repository;


import com.example.mqttserver.entity.ClientConnection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientConnectionRepository extends JpaRepository<ClientConnection, Long> {
}


