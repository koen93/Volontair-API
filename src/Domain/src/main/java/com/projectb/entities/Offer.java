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
public class Offer extends AbsTask {

}
