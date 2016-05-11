package com.projectb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projectb.abs.AbsEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Account")
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

    @Column(length = 255, nullable = false)
    @Setter
    @NotNull
    @Size(min = 8)
    private String name;

    @Column(length = 512)
    @Setter
    @Size(max = 512)
    private String summary;

    @ManyToMany
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "listener", cascade = CascadeType.DETACH)
    @Setter
    private List<Conversation> listenerConversations = new ArrayList<>(); // TODO: Should be Set?

    @OneToMany(mappedBy = "starter", cascade = CascadeType.DETACH)
    @Setter
    private List<Conversation> starterConversations = new ArrayList<>(); // TODO: Should be Set?

    @OneToMany(mappedBy = "creator", cascade = CascadeType.DETACH)
    private List<Offer> offers = new ArrayList<>(); // TODO: Should be Set?

    @OneToMany(mappedBy = "creator", cascade = CascadeType.DETACH)
    private List<Request> requests = new ArrayList<>(); // TODO: Should be Set?

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonIgnore
    private List<Role> roles = new ArrayList<>(); // TODO: Should be Set?

    @Setter
    private boolean enabled;
}
