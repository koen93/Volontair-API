package com.projectb.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class ConnectionId implements Serializable {
    private String userId;
    private String providerId;
    private String providerUserId;
}
