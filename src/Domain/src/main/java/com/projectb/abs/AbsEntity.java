package com.projectb.abs;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.Date;

@Getter
@MappedSuperclass
public abstract class AbsEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Setter
    private Date updated;

}
