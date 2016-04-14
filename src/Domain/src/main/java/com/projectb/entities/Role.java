package com.projectb.entities;

import com.projectb.abs.AbsEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Role extends AbsEntity {
    @Column(nullable = false)
    @Setter
    private String name;

    @ManyToMany
    private List<User> user = new ArrayList<>();

    public Role(String name) {
        this.name = name;
    }
}
