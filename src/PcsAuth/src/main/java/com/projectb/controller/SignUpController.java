package com.projectb.controller;

import com.projectb.auth.SignInService;
import com.projectb.repositories.UserRepo;
import edu.emory.mathcs.backport.java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SocialUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

@Controller
public class SignUpController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SignInService signInService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public void signupForm(WebRequest request, Model model) {
        Connection<?> connectionFromSession = providerSignInUtils.getConnectionFromSession(request);

        SignUpForm form = new SignUpForm();
        if(connectionFromSession != null) {
            form = SignUpForm.fromProviderUser(connectionFromSession.fetchUserProfile());
        }
        model.addAttribute("form", form);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@ModelAttribute SignUpForm form, WebRequest request) {
        SocialUser user = createUser(form);
        if(user != null) {
            signInService.signIn(user.getUsername());
            providerSignInUtils.doPostSignUp(user.getUsername(), request);
            return "redirect:/";
        }
        return null;
    }

    @RequestMapping(value = "/signup")
    public String signup() {
        return "signup";
    }

    private SocialUser createUser(SignUpForm form) {
        SocialUser user = new SocialUser(
                form.getUsername(),
                form.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
        userDetailsManager.createUser(user);
        return user;
    }
}
