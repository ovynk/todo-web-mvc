package com.oleksiy.todo.controller;

import com.oleksiy.todo.model.chat.ChatMessage;
import com.oleksiy.todo.service.ChatMassageService;
import com.oleksiy.todo.service.ChatRoomService;
import com.oleksiy.todo.service.ToDoService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class ChatController {

    private final ChatMassageService chatMessageService;
    private final ToDoService toDoService;

    @MessageMapping("/chat/todos/{todoId}.sendMessage")
    @SendTo("/topic/todos/{todoId}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage, @DestinationVariable String todoId) {
        chatMessage.setChatroom(toDoService.readById(Long.parseLong(todoId)).getChatRoom());

        chatMessageService.create(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/chat/todos/{todoId}.addUser")
    @SendTo("/topic/todos/{todoId}")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               @DestinationVariable String todoId,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add attributes in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("todoId", todoId);
        return chatMessage;
    }

}