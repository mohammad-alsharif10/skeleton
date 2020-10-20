package com.skeleton.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {

    private boolean errorStatus;
    private Integer responseStatus;
    private String message;

    BaseResponse(boolean errorStatus, Integer responseStatus, String message) {
        this.errorStatus = errorStatus;
        this.responseStatus = responseStatus;
        this.message = message;
    }


    BaseResponse(boolean errorStatus, Integer message) {
        this.errorStatus = errorStatus;
        this.responseStatus = message;
    }


    BaseResponse() {
    }

}
