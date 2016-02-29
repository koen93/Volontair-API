package com.projectb.entities;

import com.projectb.abs.AbsEntity;
import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
public class Tag extends AbsEntity {

    @NotNull
    @Setter
    private String title;
}
