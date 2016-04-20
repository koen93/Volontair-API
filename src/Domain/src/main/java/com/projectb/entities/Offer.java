package com.projectb.entities;

import com.projectb.abs.AbsEntity;
import com.projectb.abs.AbsTask;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@Getter
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Offer.findAllOpen",
                query = "select o from Offer o where o.closed = false"),
        @NamedQuery(name = "Offer.findAllClosed",
                query = "select o from Offer o where o.closed = true")
})
public class Offer extends AbsTask {

}
