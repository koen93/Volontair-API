package com.projectb.auth;

import com.projectb.entities.*;
import com.projectb.repositories.ConnectionRepo;
import org.springframework.social.connect.*;
import org.springframework.social.connect.Connection;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class SimpleConnectionRepository implements ConnectionRepository {
    private final String userId;
    private final ConnectionRepo connectionRepo;
    private final ConnectionFactoryLocator connectionFactoryLocator;

    public SimpleConnectionRepository(String userId, ConnectionRepo connectionRepo, ConnectionFactoryLocator connectionFactoryLocator) {
        this.userId = userId;
        this.connectionRepo = connectionRepo;
        this.connectionFactoryLocator = connectionFactoryLocator;
    }

    @Override
    public MultiValueMap<String, Connection<?>> findAllConnections() {
        List<com.projectb.entities.Connection> resultList = connectionRepo.findByUserIdOrderByProviderIdAscRankAsc(userId);
        List<Connection<?>> connectionList = toConnectionList(resultList);
        MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<>();

        Set<String> registeredProviderIds = connectionFactoryLocator.registeredProviderIds();
        for(String registeredProviderId : registeredProviderIds) {
            connections.put(registeredProviderId, Collections.emptyList());
        }

        for(Connection<?> connection : connectionList) {
            String providerId = connection.getKey().getProviderId();
            if(connections.get(providerId).size() == 0)
                connections.put(providerId, new LinkedList<Connection<?>>());
            connections.add(providerId, connection);
        }

        return connections;
    }

    @Override
    public List<Connection<?>> findConnections(String providerId) {
        List<com.projectb.entities.Connection> resultList = connectionRepo.findByUserIdAndProviderIdOrderByRank(userId, providerId);
        List<Connection<?>> connectionList = toConnectionList(resultList);

        return connectionList;
    }

    @Override
    public <A> List<Connection<A>> findConnections(Class<A> apiType) {
        List<?> connections = findConnections(getProviderId(apiType));
        return (List<Connection<A>>) connections;
    }

    @Override
    public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUserIds) {
        throw new UnsupportedOperationException("Not implemented, yet.");
    }

    @Override
    public Connection<?> getConnection(ConnectionKey connectionKey) {
        com.projectb.entities.Connection result = connectionRepo.findByUserIdAndProviderIdAndProviderUserId(userId, connectionKey.getProviderId(), connectionKey.getProviderUserId());
        return toConnection(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
        String providerId = getProviderId(apiType);
        return (Connection<A>) getConnection(new ConnectionKey(providerId, providerUserId));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
        String providerId = getProviderId(apiType);
        Connection<A> connection = (Connection<A>) findPrimaryConnection(providerId);
        if (connection == null) {
            throw new NotConnectedException(providerId);
        }
        return connection;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
        String providerId = getProviderId(apiType);
        return (Connection<A>) findPrimaryConnection(providerId);
    }

    @Override
    public void addConnection(Connection<?> connection) {
        throw new UnsupportedOperationException("Not implemented, yet.");
    }

    @Override
    public void updateConnection(Connection<?> connection) {
        throw new UnsupportedOperationException("Not implemented, yet.");
    }

    @Override
    public void removeConnections(String providerId) {
        throw new UnsupportedOperationException("Not implemented, yet.");
    }

    @Override
    public void removeConnection(ConnectionKey connectionKey) {
        throw new UnsupportedOperationException("Not implemented, yet.");
    }

    private List<Connection<?>> toConnectionList(List<com.projectb.entities.Connection> resultList) {
        return resultList.stream()
                .map(this::toConnection).collect(Collectors.toList());
    }

    private Connection<?> toConnection(com.projectb.entities.Connection connection) {
        ConnectionData connectionData = new ConnectionData(
                connection.getProviderId(),
                connection.getProviderUserId(),
                connection.getDisplayName(),
                connection.getProfileUrl(),
                connection.getImageUrl(),
                connection.getAccessToken(),
                connection.getSecret(),
                connection.getRefreshToken(),
                connection.getExpireTime()
        );
        ConnectionFactory<?> connectionFactory= connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId());
        return connectionFactory.createConnection(connectionData);
    }

    private <A> String getProviderId(Class<A> apiType) {
        return connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
    }

    private Connection<?> findPrimaryConnection(String providerId) {
        throw new UnsupportedOperationException("Not implemented, yet.");
    }
}
