package com.projectb.abs;

import com.projectb.entities.Category;
import com.projectb.entities.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@MappedSuperclass
public abstract class AbsTask extends AbsEntity {
    @ManyToOne
    @Setter
    private User creator;

    @Column(nullable = false)
    @Setter
    private String title;

    @Column(nullable = false)
    @Setter
    private String description;

    @ManyToOne(cascade = CascadeType.DETACH)
    @Setter
    private Category category;

    @Setter
    private boolean closed = false;

}
