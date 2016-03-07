package com.projectb.abs;

import com.projectb.entities.Tag;
import com.projectb.entities.User;
import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@MappedSuperclass
public abstract class AbsTask extends AbsEntity {

    //TODO: Should we switch to enum TaskType instead of abstraction

    @NotNull
    @Setter
    private String description;

    @NotNull
    @OneToMany(cascade = CascadeType.DETACH)
    @Setter
    List<Tag> tagList;

    @NotNull
    @OneToMany(cascade = CascadeType.DETACH)
    @Setter
    List<User> responders;

    @Setter
    private boolean closed = false;
}
