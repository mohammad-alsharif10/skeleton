package com.skeleton.response;


import com.skeleton.dto.BaseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class SingleResult<ID extends Serializable, T extends BaseDto<ID>> extends BaseResponse implements Serializable {

    private T data;

    public SingleResult(boolean errorStatus, Integer responseStatus, String message, T data) {
        super(errorStatus, responseStatus, message);
        this.data = data;
    }

    public SingleResult(boolean errorStatus, Integer responseStatus, T data) {
        super(errorStatus, responseStatus);
        this.data = data;
    }

    public SingleResult(T model) {
        this.data = model;
    }

    public SingleResult() {

    }

}
