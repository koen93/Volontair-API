package com.projectb.abs;

import com.projectb.entities.Tag;
import com.projectb.entities.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@MappedSuperclass
public abstract class AbsTask extends AbsEntity {

    //TODO: Should we switch to enum TaskType instead of abstraction

    @Column(nullable = false)
    @Setter
    private String description;

    @Column(nullable = false)
    @OneToMany(cascade = CascadeType.DETACH)
    @Setter
    List<Tag> tagList;

    @Column(nullable = false)
    @OneToMany(cascade = CascadeType.DETACH)
    @Setter
    List<User> responders;

    @Setter
    private boolean closed = false;
}
