package com.projectb.config;

import com.projectb.auth.BasicSocialUserDetailsService;
import com.projectb.auth.BasicUserDetailsManager;
import com.projectb.auth.SignUpService;
import com.projectb.entities.Role;
import com.projectb.repositories.RoleRepo;
import com.projectb.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.social.security.SocialUserDetailsService;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private SignUpService signUpService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
            .formLogin()
                .loginPage("/signin")
                .loginProcessingUrl("/signin/authenticate")
                .failureUrl("/signin?error")
                .permitAll()
                .and();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .userDetailsService(userDetailsService);
//
        Role role = new Role("ROLE_USER");
        roleRepo.saveAndFlush(role);

        signUpService.signUp("user", "password");
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
    @Override
    public UserDetailsService userDetailsService() {
        try {
            return userDetailsManager();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean
    public UserDetailsManager userDetailsManager() throws Exception {
        return new BasicUserDetailsManager(userRepo, roleRepo, authenticationManager(), signUpService);
    }
}
