package com.projectb.entities;

import com.projectb.abs.AbsTask;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@Getter
@AllArgsConstructor
public class Request extends AbsTask {

}
