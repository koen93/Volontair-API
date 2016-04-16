package com.projectb.entities;

import com.projectb.abs.AbsEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Role extends AbsEntity implements Serializable {
    @Column(nullable = false)
    @Setter
    private String name;

    public Role(String name) {
        this.name = name;
    }
}
