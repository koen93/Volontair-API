package com.projectb.endpoint;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projectb.entities.Conversation;
import com.projectb.entities.Message;
import lombok.Getter;

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

    public void setNearbyVolonteers(int nearbyVolonteers) {
        this.nearbyVolonteers = nearbyVolonteers;
    }

    public void setPotentialContacts(int potentialContacts) {
        this.potentialContacts = potentialContacts;
    }
}
