///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.example.mqttserver.service;
//
//import org.jpos.iso.ISOChannel;
//import org.jpos.iso.ISOException;
//import org.jpos.iso.ISOMsg;
//import org.jpos.iso.channel.ASCIIChannel;
//import org.jpos.iso.packager.GenericPackager;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class Iso8583ClientService {
//
//    @Value("${iso8583.server.host}")
//    private String serverHost;
//
//    @Value("${iso8583.server.port}")
//    private int serverPort;
//
//    private ISOChannel channel;
//
//    public void connect() throws Exception {
//        GenericPackager packager = new GenericPackager("path/to/iso8583.xml"); // sesuaikan dengan file packager Anda
//        channel = new ASCIIChannel(serverHost, serverPort, packager);
//        channel.connect();
//    }
//
//    public void disconnect() throws Exception {
//        if (channel != null && channel.isConnected()) {
//            channel.disconnect();
//        }
//    }
//
//    public byte[] sendIsoMessage(ISOMsg isoMsg) throws Exception {
//        if (channel == null || !channel.isConnected()) {
//            throw new IllegalStateException("ISO8583 channel is not connected.");
//        }
//
//        channel.send(isoMsg);
//        ISOMsg response = channel.receive();
//        return response.pack();
//    }
//
//    public ISOMsg createIsoMessage() throws ISOException {
//        ISOMsg isoMsg = new ISOMsg();
//        isoMsg.setMTI("0800");
//        isoMsg.set(3, "000000");
//        isoMsg.set(4, "000000010000");
//        isoMsg.set(11, "123456");
//        isoMsg.set(41, "12345678");
//        isoMsg.set(49, "840");
//
//        return isoMsg;
//    }
//}
