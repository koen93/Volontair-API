package com.projectb.entities;

import com.projectb.abs.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User extends AbsEntity {

    @Setter
    private String userName;

    @OneToMany(cascade = CascadeType.DETACH)
    @Setter
    List<User> contacts;

}
