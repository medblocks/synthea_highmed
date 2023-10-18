package com.medblocks.highmed.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
public class WebSocketController {

    @Autowired
    private static SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public WebSocketController(SimpMessagingTemplate template) {
        WebSocketController.simpMessagingTemplate = template;
    }

    public static void updateGenerateLogs(String userID, String message){
        Map<String, String> responseObj = new HashMap<>();
        responseObj.put("userID", userID);
        responseObj.put("message", message);
        responseObj.put("type", "GENERATE");
        simpMessagingTemplate.convertAndSend("/topic/public", responseObj);
    }
    
    @MessageMapping("/addUser")
    @SendTo("/topic/public")
    public Map<String, String> addUser(@Payload Map<String, String> body,SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        String userID = body.get("userID");
        System.out.println("userID: " + userID);
        headerAccessor.getSessionAttributes().put("userID", userID);
        Map<String, String> responseObj = new HashMap<>();
        responseObj.put("userID", userID);
        responseObj.put("message", "User added");
        responseObj.put("type", "USER");
        return(responseObj);
    }

}
