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
public class SimpleSocialUserDetailsService implements SocialUserDetailsService {
    private final UserDetailsService userDetailsService;


    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        AuthUserDetails userDetails = (AuthUserDetails) userDetailsService.loadUserByUsername(userId);
        return new AuthSocialUserDetails(userDetails.getUser());
    }
}
