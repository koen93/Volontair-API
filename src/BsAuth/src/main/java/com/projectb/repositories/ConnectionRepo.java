package com.projectb.repositories;

import com.projectb.entities.Connection;
import com.projectb.repo.BasicRepo;

import java.util.List;
import java.util.Set;

public interface ConnectionRepo extends BasicRepo<Connection> {
    List<Connection> findByProviderIdAndProviderUserId(String providerId, String providerUserId);
    List<Connection> findByProviderIdAndProviderUserIdIn(String providerId, Set<String> providerUserIds);
    List<Connection> findByUserIdOrderByProviderIdAscRankAsc(String userId);
    List<Connection> findByUserIdAndProviderIdOrderByRank(String userId, String providerId);
    List<Connection> findByUserIdAndProviderUserIdIn(String userId, List<String> providerUserId);
    Connection findByUserIdAndProviderIdAndProviderUserId(String userId, String providerId, String providerUserId);
}
