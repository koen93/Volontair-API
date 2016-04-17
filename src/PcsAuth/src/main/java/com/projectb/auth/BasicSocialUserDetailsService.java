package com.projectb.auth;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * Loads a social user by its username. Useful when using {@link org.springframework.social.security.AuthenticationNameUserIdSource}
 * as the {@link org.springframework.social.UserIdSource}.
 */
@AllArgsConstructor
public final class BasicSocialUserDetailsService implements SocialUserDetailsService {
    private final UserDetailsService userDetailsService;

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        return (SocialUserDetails) userDetailsService.loadUserByUsername(userId);
    }
}
