package com.projectb.abs;

import com.projectb.entities.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@MappedSuperclass
public abstract class AbsTask extends AbsEntity {
    @Column(nullable = false)
    @Setter
    private String title;

    @Column(nullable = false)
    @Setter
    private String description;

    @OneToOne(cascade = CascadeType.DETACH)
    @Setter
    private Category category;

    @Setter
    private Double latitude;

    @Setter
    private Double longitude;

    @Setter
    private boolean closed = false;

}
