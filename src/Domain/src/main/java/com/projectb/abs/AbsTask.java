package com.projectb.abs;

import com.projectb.entities.Category;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

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

    @Column(nullable = false)
    @Type(type="org.hibernate.spatial.GeometryType")
    @Setter
    private Point location;

    @Setter
    private Double latitude;

    @Setter
    private Double longitude;

    @Setter
    private boolean closed = false;

}
