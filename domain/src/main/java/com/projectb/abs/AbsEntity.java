package com.projectb.abs;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@Getter
@MappedSuperclass
public abstract class AbsEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private int version;
}
