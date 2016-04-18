package com.projectb.abs;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
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

    private Date created = new Date();

    @Setter
    private Date updated;

}
