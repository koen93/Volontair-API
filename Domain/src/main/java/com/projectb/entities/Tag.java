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
public class Tag extends AbsEntity {

    @Column(nullable = false)
    @Setter
    private String title;
}
