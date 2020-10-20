package com.skeleton.response;

import com.skeleton.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class PageResult<ID extends Serializable, T extends BaseDto<ID>> extends BaseResponse implements Serializable {

    private List<T> data;
    private Long totalElements;
    private int numberOfPages;

    public PageResult(boolean errorStatus, Integer responseStatus, String message, List<T> data, Long totalElements, int numberOfPages) {
        super(errorStatus, responseStatus, message);
        this.data = data;
        this.totalElements = totalElements;
        this.numberOfPages = numberOfPages;
    }

    public PageResult(boolean errorStatus, Integer responseStatus, List<T> data, Long totalElements, int numberOfPages) {
        super(errorStatus, responseStatus);
        this.data = data;
        this.totalElements = totalElements;
        this.numberOfPages = numberOfPages;
    }

    public PageResult(List<T> data, Long totalElements, int numberOfPages) {
        this.data = data;
        this.totalElements = totalElements;
        this.numberOfPages = numberOfPages;
    }

    public PageResult(boolean errorStatus, Integer responseStatus, String message, List<T> data) {
        super(errorStatus, responseStatus, message);
        this.data = data;
    }

    public PageResult() {
    }


}
