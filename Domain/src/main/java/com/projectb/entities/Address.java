package com.projectb.entities;

import com.projectb.abs.AbsEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
public class Address extends AbsEntity {

    @Column(nullable = false)
    @Setter
    private String street;

    @Column(nullable = false)
    @Setter
    private int streetNumber;

    @Setter
    private int streetNumberAddition;

    @Column(nullable = false)
    @Setter
    private String zipCode;

    @Column(nullable = false)
    @Setter
    private int city;

    @Column(nullable = false)
    @Setter
    private String country;
}
