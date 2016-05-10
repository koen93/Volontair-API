package com.projectb.config;

import com.projectb.auth.*;
import com.projectb.entities.Account;
import com.projectb.entities.Role;
import com.projectb.repositories.RoleRepo;
import com.projectb.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.social.security.SocialUserDetailsService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String URL_SIGNIN = "/signin";

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    @Bean
    public SignUpService signUpService() {
        return new BasicSignUpService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());
        http
            .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
            .formLogin()
                .loginPage(URL_SIGNIN)
                .loginProcessingUrl(URL_SIGNIN + "/authenticate")
                .failureUrl(URL_SIGNIN + "?error")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());

        Role role = new Role("ROLE_USER");
        roleRepo.saveAndFlush(role);

        Account account = new Account();
        account.setUsername("account");
        account.setPassword("password");
        account.setName("Sara Tancredi");
        account.setSummary("Lorum ipsum dolor sit amet.");

        signUpService().signUp(account);
    }

    @Bean(name="authenticationManagerBean")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SocialUserDetailsService socialUserDetailsService() throws Exception {
        return new BasicSocialUserDetailsService(userDetailsService());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetailsService userDetailsService() {
        BasicUserDetailsManager basicUserDetailsManager = new BasicUserDetailsManager();
        autowireCapableBeanFactory.autowireBean(basicUserDetailsManager);

        return basicUserDetailsManager;
    }

    @Bean
    public LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint() {
        return new BasicLoginUrlAuthenticationEntryPoint(URL_SIGNIN);
    }
}
