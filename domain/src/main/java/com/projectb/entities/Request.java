package com.projectb.entities;

import com.projectb.abs.AbsTask;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@NoArgsConstructor
public class Request extends AbsTask {
    //TODO: Think about specific Request properties?
}
