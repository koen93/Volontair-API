package com.projectb.config;

import com.projectb.auth.SignUpService;
import com.projectb.auth.UserConnectionSignUp;
import com.projectb.exception.SecretPasswordNotSetException;
import com.projectb.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import javax.sql.DataSource;

@Configuration
@EnableSocial
public class SocialConfig implements SocialConfigurer {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private Environment environment;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        connectionFactoryConfigurer.addConnectionFactory(new FacebookConnectionFactory(
                environment.getProperty("facebook.clientId"),
                environment.getProperty("facebook.clientSecret")
        ));
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        String salt = KeyGenerators.string().generateKey();
        String secretPassword = environment.getProperty("security.secretPassword");
        if(secretPassword == null)
            throw new SecretPasswordNotSetException();

        TextEncryptor standard = Encryptors.queryableText(environment.getProperty("security.secretPassword"), salt);
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, standard);
        repository.setConnectionSignUp(connectionSignUp());
        return repository;
    }

    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository) {
        return new ProviderSignInUtils(connectionFactoryLocator, usersConnectionRepository);
    }

    @Bean
    public ConnectionSignUp connectionSignUp() {
        return new UserConnectionSignUp(userRepo, signUpService);
    }

}
