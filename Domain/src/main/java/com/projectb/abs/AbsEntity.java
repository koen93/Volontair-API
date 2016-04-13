package com.projectb.abs;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.Date;

@Getter
@MappedSuperclass
public abstract class AbsEntity {

    @Id
    @GeneratedValue
    private Long id;

    @GeneratedValue
    private Date created;

    @GeneratedValue
    private Date updated;

    @Version
    private int version;
}
