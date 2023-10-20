package com.medblocks.highmed.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.Map;

import org.mitre.synthea.engine.SocketLogger;




@Service
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Content-Disposition")
public class SpringWebSocketLogger implements SocketLogger {

    @Autowired
    private SimpMessagingTemplate template;

    @Override
    public void sendLog(String message, String userID) {
      Map<String, String> responseObj = new HashMap<>();
      responseObj.put("userID", userID);
      responseObj.put("message", message);
      responseObj.put("type", "GENERATE");
      template.convertAndSend("/topic/public", responseObj);
    }
}