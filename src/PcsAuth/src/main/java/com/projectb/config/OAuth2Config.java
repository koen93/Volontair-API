package com.projectb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.*;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

@Configuration
public class OAuth2Config {

    private static final String VOLONTAIR_RESOURCE_ID = "volontair";

    @Bean
    public LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint() {
        return new LoginUrlAuthenticationEntryPoint("/signin");
    }

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired
        private LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint;

        @Autowired
        private DaoAuthenticationProvider daoAuthenticationProvider;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources
                    .resourceId(VOLONTAIR_RESOURCE_ID)
                    .authenticationEntryPoint(loginUrlAuthenticationEntryPoint)
                    .stateless(false);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authenticationProvider(daoAuthenticationProvider);
            http.userDetailsService(userDetailsService);

            http
                .authorizeRequests()
                    .antMatchers("/", "/signup**", "/signin**", "/auth/facebook/client**", "/webjars/**", "/login", "/disclaimer")
                    .permitAll()
                    .and()
                .formLogin()
                    .loginPage("/signin")
                    .loginProcessingUrl("/signin/authenticate")
                    .failureUrl("/signin?error")
                    .permitAll()
                    .and()
                .rememberMe()
                    .and()
                .logout()
                    .logoutUrl("/signout")
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                    .and()
                .authorizeRequests()
                    .antMatchers("/connect**")
                    .authenticated()
                    .and()
                .authorizeRequests()
                    .antMatchers("/api/v1")
                    .authenticated()
                    .and()
                .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and()
//                .csrf()
//                    .csrfTokenRepository(csrfTokenRepository())
//                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and()
                .apply(new SpringSocialConfigurer()).signupUrl("/signup");
        }

        private CsrfTokenRepository csrfTokenRepository() {
            return new HttpSessionCsrfTokenRepository();
        }

    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration implements AuthorizationServerConfigurer {
        @Autowired
        private DataSource dataSource;

        @Autowired
        private TokenStore tokenStore;

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Autowired
        private LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint;

        @Override
        public void configure(AuthorizationServerSecurityConfigurer authorizationServerSecurityConfigurer) throws Exception {
            authorizationServerSecurityConfigurer
                    .allowFormAuthenticationForClients()
                    .authenticationEntryPoint(loginUrlAuthenticationEntryPoint);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clientDetailsServiceConfigurer) throws Exception {
            clientDetailsServiceConfigurer.jdbc(dataSource)
                    .withClient("volontair")
                    .authorizedGrantTypes("authorization_code", "implicit", "client_credentials")
                    .authorities("ROLE_USER")
                    .autoApprove(true)
                    .scopes("test")
                    .secret("secret");
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer authorizationServerEndpointsConfigurer) throws Exception {
            authorizationServerEndpointsConfigurer
                    .tokenStore(tokenStore())
                    .authenticationManager(authenticationManager);
        }

        @Bean
        public TokenStore tokenStore() {
            return new JdbcTokenStore(dataSource);
        }
    }
}
