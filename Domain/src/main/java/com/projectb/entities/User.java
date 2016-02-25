package com.projectb.entities;

import com.projectb.abs.AbsEntity;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class User extends AbsEntity {

    @NotNull
    @Setter
    private String userName;

    @Nullable
    @Setter
    private Address address;

    @Nullable
    @Setter
    List<User> buddyList;
}
