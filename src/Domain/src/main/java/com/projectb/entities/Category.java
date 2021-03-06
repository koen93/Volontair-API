package com.projectb.entities;

import com.projectb.abs.AbsEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Category extends AbsEntity {
    @Column(nullable = false)
    @Setter
    public String name;

    @Column(nullable = false)
    @Setter
    public String iconKey = "default";

    @Column(nullable = false)
    @Setter
    public String colorHex = "#9E9E9E";
}
