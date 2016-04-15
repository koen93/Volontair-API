package com.projectb.controller;

import com.projectb.entities.User;
import com.projectb.repositories.UserRepo;
import edu.emory.mathcs.backport.java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import javax.xml.ws.spi.Provider;

@Controller
public class SignupController {
    private final ProviderSignInUtils providerSignInUtils;
    private final UserDetailsManager userDetailsManager;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    public SignupController(ProviderSignInUtils providerSignInUtils, UserDetailsManager userDetailsManager) {
        this.providerSignInUtils = providerSignInUtils;
        this.userDetailsManager = userDetailsManager;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public void signupForm(WebRequest request, Model model) {
        Connection<?> connectionFromSession = providerSignInUtils.getConnectionFromSession(request);

        SignupForm form = new SignupForm();
        if(connectionFromSession != null) {
            form = SignupForm.fromProviderUser(connectionFromSession.fetchUserProfile());
        }
        model.addAttribute("form", form);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@ModelAttribute SignupForm form, WebRequest request) {
        SocialUser user = createUser(form);
        if(user != null) {
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities()));
            providerSignInUtils.doPostSignUp(user.getUsername(), request);
            return "redirect:/";
        }
        return null;
    }

    private SocialUser createUser(SignupForm form) {
        SocialUser user = new SocialUser(
                form.getUsername(),
                form.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
        userDetailsManager.createUser(user);
        return user;
    }
}
