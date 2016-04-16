package com.projectb.auth;

import com.projectb.repositories.ConnectionRepo;
import edu.emory.mathcs.backport.java.util.Arrays;
import edu.emory.mathcs.backport.java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.*;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//@Repository
public class SimpleUsersConnectionRepository implements UsersConnectionRepository {

    private final ConnectionRepo connectionRepo;

    private ConnectionSignUp connectionSignUp;

    private ConnectionFactoryLocator connectionFactoryLocator;

    @Autowired
    public SimpleUsersConnectionRepository(ConnectionRepo connectionRepo, ConnectionFactoryLocator connectionFactoryLocator) {
        this.connectionRepo = connectionRepo;
        this.connectionFactoryLocator = connectionFactoryLocator;
    }

    public void setConnectionSignUp(ConnectionSignUp connectionSignUp) {
        this.connectionSignUp = connectionSignUp;
    }

    @Override
    public List<String> findUserIdsWithConnection(Connection<?> connection) {
        ConnectionKey key = connection.getKey();

        List<com.projectb.entities.Connection> localConnections = connectionRepo.findByProviderIdAndProviderUserId(key.getProviderId(), key.getProviderUserId());
        List<String> userIds = localConnections.stream().map(com.projectb.entities.Connection::getUserId).collect(Collectors.toList());
        if(userIds.size() == 0 && connectionSignUp != null) {
            String newUserId = connectionSignUp.execute(connection);
            if(newUserId != null) {
                createConnectionRepository(newUserId).addConnection(connection);
                return Collections.singletonList(newUserId);
            }
        }
        return userIds;
    }

    @Override
    public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
        List<com.projectb.entities.Connection> localConnection = connectionRepo.findByProviderIdAndProviderUserIdIn(providerId, providerUserIds);
        return localConnection.stream().map(com.projectb.entities.Connection::getUserId).collect(Collectors.toSet());
    }

    @Override
    public ConnectionRepository createConnectionRepository(String userId) {
        if(userId == null)
            throw new IllegalArgumentException("userId cannot be null");
        return new SimpleConnectionRepository(userId, connectionRepo, connectionFactoryLocator);
    }
}
