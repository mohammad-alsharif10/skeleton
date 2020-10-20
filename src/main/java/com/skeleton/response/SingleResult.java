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

    private T baseDto;

    public SingleResult(boolean errorStatus, Integer responseStatus, String message, T baseDto) {
        super(errorStatus, responseStatus, message);
        this.baseDto = baseDto;
    }

    public SingleResult(boolean errorStatus, Integer responseStatus, T baseDto) {
        super(errorStatus, responseStatus);
        this.baseDto = baseDto;
    }

    public SingleResult(T model) {
        this.baseDto = model;
    }

    public SingleResult() {

    }

}
