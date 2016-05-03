package com.projectb.endpoint;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projectb.entities.Message;
import lombok.Getter;
import sun.plugin2.message.Conversation;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Dashboard {
    @JsonProperty("nearbyVolonteers")
    private int nearbyVolonteers;

    @JsonProperty("potentialContacts")
    private int potentialContacts;

    @JsonProperty("messages")
    private List<Message> messages = new ArrayList<>();

    @JsonProperty("connections")
    private List<Conversation> connections = new ArrayList<>();
}
