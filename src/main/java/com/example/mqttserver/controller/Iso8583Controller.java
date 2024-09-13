///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.example.mqttserver.controller;
//
//import com.example.mqttserver.service.Iso8583ClientService;
//import org.jpos.iso.ISOMsg;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/iso8583")
//public class Iso8583Controller {
//
//    @Autowired
//    private Iso8583ClientService iso8583ClientService;
//
//    @GetMapping("/send")
//    public String sendIsoMessage() {
//        try {
//            iso8583ClientService.connect();
//
//            ISOMsg isoMsg = iso8583ClientService.createIsoMessage();
//            byte[] response = iso8583ClientService.sendIsoMessage(isoMsg);
//
//            iso8583ClientService.disconnect();
//            return "Received response: " + response;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error sending ISO8583 message: " + e.getMessage();
//        }
//    }
//}
