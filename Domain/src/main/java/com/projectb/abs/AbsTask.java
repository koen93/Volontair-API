package com.projectb.abs;

import com.projectb.entities.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
public abstract class AbsTask extends AbsEntity {
    @Column(nullable = false)
    @Setter
    private String title;

    @Column(nullable = false)
    @Setter
    private String description;

    @Column(nullable = false)
    @Setter
    private Category category;

    private Double latitude;
    private Double longitude;

    @Setter
    private boolean closed = false;
}
