package com.projectb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projectb.abs.AbsEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User extends AbsEntity implements Serializable {

    @Column
    @Setter
    private String username;

    @Column(length = 60)
    @Setter
    @JsonIgnore
    private String password;

    @Column(length = 255)
    @Setter
    private String name;

    @Column(length = 255)
    @Setter
    private String avatar;

    @Column(length = 512)
    @Setter
    private String summary;

    @ManyToMany
    private List<Category> categories = new ArrayList<>();

//    @OneToMany(cascade = CascadeType.DETACH)
//    @Setter
//    private List<Conversation> conversations = new ArrayList<>(); // TODO: Should be Set?

    @OneToMany
    private List<Offer> offers = new ArrayList<>();

    @OneToMany
    private List<Request> requests = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonIgnore
    private List<Role> roles = new ArrayList<>(); // TODO: Should be Set?

    @Setter
    private boolean enabled;
}
