package com.projectb.entities;

import lombok.Getter;

import javax.persistence.*;

@Entity
@IdClass(ConnectionId.class)
@Table(name = "UserConnection", uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "providerId", "rank"}))
@Getter
public class Connection {
    @Id
    @Column(length = 255, nullable = false)
    private String userId;

    @Id
    @Column(length = 255, nullable = false)
    private String providerId;

    @Id
    @Column(length = 255, nullable = false)
    private String providerUserId;

    @Column(nullable = false)
    private int rank;

    @Column(length = 255)
    private String displayName;

    @Column(length = 512)
    private String profileUrl;

    @Column(length = 512)
    private String imageUrl;

    @Column(length = 512, nullable = false)
    private String accessToken;

    @Column(length = 512)
    private String secret;

    @Column(length = 512)
    private String refreshToken;

    @Column
    private long expireTime;
}
