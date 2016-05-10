package com.projectb.entities;

import com.projectb.abs.AbsEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"listener_id", "starter_id"}))
public class Conversation extends AbsEntity {
    @ManyToOne
    @Setter
    private Account starter;

    @ManyToOne
    @Setter
    private Account listener;

    @OneToMany(mappedBy = "conversation")
    private Set<Message> messages = new LinkedHashSet<>();

    public void addMessage(Message message) {
        message.setConversation(this);
        messages.add(message);
    }
}
