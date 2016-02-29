package com.projectb.endpoint;

import com.projectb.entities.User;
import com.projectb.repo.BasicRepo;
import com.projectb.servicerepo.UserRepo;
import com.projectb.vault.AbsRestVault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class UserService extends AbsRestVault<User> {

    //Talk to this repo bean which interfere with the awesome vault
    @Autowired
    private UserRepo userRepo;

    @Override
    public BasicRepo<User> provideRepo() {
//        return userRepo;
        return null;
    }
}
