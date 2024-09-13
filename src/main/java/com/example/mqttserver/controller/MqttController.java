/////*
//// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
//// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
//// */
//package com.example.mqttserver.controller;
//
//import com.example.mqttserver.service.PublisherService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.util.HashMap;
//import org.eclipse.paho.client.mqttv3.MqttException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@CrossOrigin(origins = "*")
//public class MqttController {
//
//    @Autowired
//    private PublisherService publisherService;
//    HashMap resp = new HashMap();
//
//    @PostMapping("/publish")
//    public HashMap publishMessage(@RequestBody String jsonPayload) {
//        try {
//            System.out.println("ini jsonpayload : "+jsonPayload);
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.readTree(jsonPayload);
//            System.out.println("ini jsonNode : "+jsonNode);
//
//            String vendor_id = jsonNode.get("vendor_id").asText();
//
//            switch (vendor_id) {
//                case "qr75" -> {
//                    String terminal_id = jsonNode.get("terminal_id").asText();
//                    String amount = jsonNode.get("amount").asText();
//                    String topic = "topic/down/" + terminal_id + "";
//                    String payload = "{\"context\":{\"sc\":\"" + amount + "\"}}";
//                    
//                    
//                    
//                    System.out.println("ini topic : " + topic);
//                    System.out.println("ini message string : " + payload);
//                    publisherService.publish(topic, payload);
//                    resp.put("resp_code", "00");
//                    resp.put("resp_desc", "successfully");
//                }
//                case "cs70" -> {
//                    String trade_id = jsonNode.get("trade_id").toString();
//                    String terminal_id = jsonNode.get("terminal_id").asText();
//                    String app_id = "PAX_CS70";
//                    String result = jsonNode.get("result").toString();
//                    String amount = jsonNode.get("amount").toString();
//                    String time = jsonNode.get("time").toString();
//                    String topic = "/" + app_id + "/trade/" + terminal_id;
//                    HashMap msg = new HashMap();
//                    msg.put("'TradeID'", trade_id);
//                    msg.put("'AppID'", app_id);
//                    msg.put("'Result'", result);
//                    msg.put("'Amount'", amount);
//                    msg.put("'Time'", time);
//                    String message = jsonPayload.formatted(msg);
//                    System.out.println("ini topic : " + topic);
//                    System.out.println("ini message string : " + message);
//                    //            format dari PAX
//                    // /PAX_CS70/trade/0000000001
//                    //{"TradeID": "12345678", "AppID": "PAX_CS70", "Result": "Success", "Amount": "1000", "Time": "20240822140000"}
//                    publisherService.publish(topic, message);
//                    resp.put("resp_code", "00");
//                    resp.put("resp_desc", "successfully");
//                }
//                default ->
//                    System.out.println("vendor id : " + vendor_id);
//            }
//            return resp;
//        } catch (JsonProcessingException | MqttException e) {
//            resp.put("resp_code", "01");
//            resp.put("resp_desc", e);
//            return resp;
//        }
//    }
//}


package com.example.mqttserver.controller;

import com.example.mqttserver.service.PublisherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin(origins = "*")
public class MqttController {

    @Autowired
    private PublisherService publisherService;
    private HashMap<String, String> resp = new HashMap<>();

    @PostMapping("/publish")
    public HashMap<String, String> publishMessage(@RequestBody String jsonPayload) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonPayload);

            String vendor_id = jsonNode.get("vendor_id").asText();
            String username = jsonNode.has("username") ? jsonNode.get("username").asText() : "defaultUsername"; // Ganti jika perlu
            String password = jsonNode.has("password") ? jsonNode.get("password").asText() : "defaultPassword"; // Ganti jika perlu

            switch (vendor_id) {
                case "qr75" -> {
                    String terminal_id = jsonNode.get("terminal_id").asText();
                    String amount = jsonNode.get("amount").asText();
                    String topic = "topic/down/" + terminal_id;
                    String payload = "{\"context\":{\"sc\":\"" + amount + "\"}}";

                    publisherService.publish(topic, payload);
                    resp.put("resp_code", "00");
                    resp.put("resp_desc", "successfully");
                }
                case "cs70" -> {
                    String trade_id = jsonNode.get("trade_id").asText();
                    String terminal_id = jsonNode.get("terminal_id").asText();
                    String app_id = "PAX_CS70";
                    String result = jsonNode.get("result").asText();
                    String amount = jsonNode.get("amount").asText();
                    String time = jsonNode.get("time").asText();
                    String topic = "/" + app_id + "/trade/" + terminal_id;
                    String message = String.format("{\"TradeID\": \"%s\", \"AppID\": \"%s\", \"Result\": \"%s\", \"Amount\": \"%s\", \"Time\": \"%s\"}", trade_id, app_id, result, amount, time);

                    publisherService.publish(topic, message);
                    resp.put("resp_code", "00");
                    resp.put("resp_desc", "successfully");
                }
                default -> resp.put("resp_code", "01");
            }
            return resp;
        } catch (JsonProcessingException | MqttException e) {
            resp.put("resp_code", "01");
            resp.put("resp_desc", e.getMessage());
            return resp;
        }
    }
}
