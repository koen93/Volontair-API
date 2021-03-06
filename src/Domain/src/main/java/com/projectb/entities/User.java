package com.projectb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projectb.abs.AbsEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "Account")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User extends AbsEntity implements Serializable {

    @Column(unique = true)
    @Setter
    @NotNull
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
    private Set<Category> categories = new HashSet<>();

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
    private Goal goal;

    @Setter
    private boolean enabled;

    @Setter
    private Double latitude = 0.00;

    @Setter
    private Double longitude = 0.00;

    @Setter
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    public Goal getGoal() {
        return goal;
    }

}
