package com.projectb.entities;

import com.projectb.abs.AbsEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Message extends AbsEntity {
    @Column(nullable = false)
    public String message;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    @Setter
    public Conversation conversation;

    @ManyToOne
    @Setter
    public User sender;

    public Message(User sender, String message) {
        this.sender = sender;
        this.message = message;
    }
}
