package com.projectb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.ExpiredAuthorizationException;
import org.springframework.social.UserIdSource;
import org.springframework.social.connect.*;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.support.ParameterMap;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Controller
public class SigninController {

    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    private ConnectionSignUp connectionSignUp;

    @Autowired
    private SignInAdapter signInAdapter;

    @RequestMapping(value = "/signin")
    public String signin() {
        return "signin";
    }

    @RequestMapping(value = "/auth/facebook/client")
    public String socialSigninAccessToken(@RequestParam String accessToken, NativeWebRequest request) {
        String providerId = "facebook";
        ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(providerId);

        FacebookTemplate facebookTemplate = new FacebookTemplate(accessToken);

        User profile = facebookTemplate.fetchObject("me", User.class, "id", "name", "link");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.set("input_token", accessToken);
        DebugToken debugToken = facebookTemplate.fetchObject("debug_token", DebugToken.class, map);

        String providerUserId = profile.getId();
        String displayName = profile.getName();
        String profileUrl = profile.getLink();
        String imageUrl = facebookTemplate.getBaseGraphApiUrl() + profile.getId() + "/picture";
        long expireTime = debugToken.getData().getExpiresAt() * 1000;

        ConnectionData connectionData = new ConnectionData(
                providerId,
                providerUserId,
                displayName,
                profileUrl,
                imageUrl,
                accessToken,
                null,
                null,
                expireTime
        );
        Connection<?> connection = connectionFactory.createConnection(connectionData);

        List<String> userIdsWithConnection = usersConnectionRepository.findUserIdsWithConnection(connection);
        if(userIdsWithConnection.isEmpty())
            connectionSignUp.execute(connection);

        signInAdapter.signIn(userIdsWithConnection.get(0), connection, request);

        return "redirect:/";
    }

    @Getter
    private static class DebugToken {
        private DebugData data;
    }

    @Getter
    private static class DebugData {
        // TODO: Might want to set setPropertyNamingStrategy instead?
        @JsonProperty("app_id")
        private String appId;

        @JsonProperty("application")
        private String application;

        @JsonProperty("error")
        private DebugDataError error;

        @JsonProperty("expires_at")
        private long expiresAt;

        @JsonProperty("is_valid")
        private boolean isValid;

        @JsonProperty("issued_at")
        private long issuedAt;

        @JsonProperty("profile_id")
        private String profileId;

        @JsonProperty("scopes")
        private String[] scopes;

        @JsonProperty("user_id")
        private String userId;
    }

    @Getter
    private static class DebugDataError {
        @JsonProperty("code")
        private int code;

        @JsonProperty("message")
        private String message;

        @JsonProperty("subcode")
        private int subcode;
    }
}
