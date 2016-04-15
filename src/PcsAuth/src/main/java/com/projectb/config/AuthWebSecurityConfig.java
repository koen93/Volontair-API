package com.projectb.config;

import com.projectb.auth.SimpleSocialUserDetailsService;
import com.projectb.auth.SimpleUserDetailsManager;
import com.projectb.entities.Role;
import com.projectb.entities.User;
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
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

@Configuration
public class AuthWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.userDetailsService(userDetailsService());
        http.authorizeRequests()
                .antMatchers("/signup")
                .permitAll()
                .and()
            .authorizeRequests()
                .antMatchers("/**")
                .hasRole("USER")
                .and()
            .formLogin()
                .loginPage("/signin")
                .loginProcessingUrl("/signin/authenticate")
                .failureUrl("/signin?param.error=bad_credentials")
                .permitAll()
                .and()
            .rememberMe()
                .and()
            .logout()
                .logoutUrl("/signout")
                .deleteCookies("JSESSIONID")
                .permitAll()
            .and()
                .apply(new SpringSocialConfigurer()).signupUrl("/signup");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .userDetailsService(userDetailsService);
//
        try {
            User user = new User();
            user.setUsername("user");
            user.setPassword("password");
            user.getRoles().add(new Role("ROLE_USER"));

            userRepo.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    public SocialUserDetailsService socialUserDetailsService() {
        return new SimpleSocialUserDetailsService(userDetailsService());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new SimpleUserDetailsManager(userRepo, roleRepo, authenticationManager);
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        return new SimpleUserDetailsManager(userRepo, roleRepo, authenticationManager);
    }
}
