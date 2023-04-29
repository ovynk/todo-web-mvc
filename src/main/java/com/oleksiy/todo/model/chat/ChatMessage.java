package com.oleksiy.todo.model.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "messages")
@Getter
@Setter
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @Column(name = "content", columnDefinition = "TEXT", length = 480)
    private String content;

    @Column(name = "sender")
    private String sender;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatroom;

}