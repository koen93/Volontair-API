package com.projectb.entities;

import com.projectb.abs.AbsEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Table(uniqueConstraints=
    @UniqueConstraint(columnNames = {"reporter_id", "reportee_id"}))
public class Report extends AbsEntity {
    @ManyToOne(optional = false)
    @Setter
    public Account reporter;

    @ManyToOne(optional = false)
    @Setter
    public Account reportee;

    @Column(nullable = false)
    public String reason;
}
