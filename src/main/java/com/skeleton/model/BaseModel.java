package com.skeleton.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@MappedSuperclass
@Getter
@Setter
public class BaseModel<ID extends Serializable> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private ID id;
    private Integer createdBy;
    private Integer updatedBy;
    private Date updateDate;
    private Date creationDate;


}
