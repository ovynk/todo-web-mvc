package com.oleksiy.todo.controller;

import com.oleksiy.todo.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat/todos/{todoId}.sendMessage")
    @SendTo("/topic/todos/{todoId}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage, @DestinationVariable String todoId) {
        return chatMessage;
    }

    @MessageMapping("/chat/todos/{todoId}.addUser")
    @SendTo("/topic/todos/{todoId}")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               @DestinationVariable String todoId,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        System.out.println(headerAccessor.getDestination());

        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

}