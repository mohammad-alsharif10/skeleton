package com.skeleton.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
public class BaseDto<ID extends Serializable> implements Serializable {

    private ID id;
    private Integer createdBy;
    private Integer updatedBy;
    private Date updateDate;
    private Date creationDate;

}
