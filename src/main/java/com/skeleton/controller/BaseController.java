package com.skeleton.controller;


import com.skeleton.dto.BaseDto;
import com.skeleton.model.BaseModel;
import com.skeleton.response.BaseResponse;
import com.skeleton.response.PageResult;
import com.skeleton.response.ResponseKeys;
import com.skeleton.response.SingleResult;
import com.skeleton.service.BaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;

public abstract class BaseController<ID extends Serializable, baseModel extends BaseModel<ID>, baseDto extends BaseDto<ID>> {


    public abstract BaseService<ID, baseModel, baseDto> getBaseService();

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public ResponseEntity<PageResult<ID, baseDto>> listAll(@RequestParam("page") int page,
                                                           @RequestParam("size") int size,
                                                           @RequestParam(required = false, value = "sort") String sort) {

        return getBaseService().findAll(page, size, sort);
    }

    @RequestMapping(path = "/find", method = RequestMethod.GET)
    public ResponseEntity<SingleResult<ID, baseDto>> findById(@RequestParam(name = "id") ID id) {
        return getBaseService().findById(id);
    }


    @RequestMapping(path = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<SingleResult<ID, baseDto>> deleteEntity(@RequestParam("id") ID entityId) {
        return getBaseService().delete(entityId);
    }
}
