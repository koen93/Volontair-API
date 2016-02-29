package com.projectb.entities;

import com.projectb.abs.AbsEntity;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
public class Address extends AbsEntity {

    @NotNull
    @Setter
    private String street;

    @NotNull
    @Setter
    private int streetNumber;

    @Nullable
    @Setter
    private int streetNumberAddition;

    @NotNull
    @Setter
    private String zipCode;

    @NotNull
    @Setter
    private int city;

    @NotNull
    @Setter
    private String country;
}
