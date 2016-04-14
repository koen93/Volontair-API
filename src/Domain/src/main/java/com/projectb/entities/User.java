package com.projectb.entities;

import com.projectb.abs.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User extends AbsEntity {

    @Setter
    private String username;

    @Column(nullable = false)
    @Setter
    private String password;

    @OneToMany(cascade = CascadeType.DETACH)
    @Setter
    private List<User> contacts = new ArrayList<>(); // TODO: Should be Set?

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Role> roles = new ArrayList<>(); // TODO: Should be Set?

    @Setter
    private boolean enabled;
}
