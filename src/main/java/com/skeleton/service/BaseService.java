package com.skeleton.service;

import com.skeleton.database.BaseRepository;
import com.skeleton.dto.BaseDto;
import com.skeleton.mapper.BaseMapper;
import com.skeleton.model.BaseModel;
import com.skeleton.response.PageResult;
import com.skeleton.response.ResponseKeys;
import com.skeleton.response.SingleResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Optional;

public abstract class BaseService<ID extends Serializable, baseModel extends BaseModel<ID>, baseDto extends BaseDto<ID>> {

    public abstract BaseRepository<baseModel, ID> getRepository();

    public abstract BaseMapper<ID, baseDto, baseModel> getBaseMapper();

    public baseDto createEntity(baseDto baseDto) {
        return getBaseMapper().toBaseDto(getRepository().save(getBaseMapper().toBaseEntity(baseDto)));
    }

    public baseDto updateEntity(baseDto baseDto) {
        return getBaseMapper().toBaseDto(getRepository().save(getBaseMapper().toBaseEntity(baseDto)));
    }

    public PageResult<ID, baseDto> findAll(Integer pageNumber, Integer size, String sort) {
        if (sort == null) {
            Page<baseModel> page = getRepository().findAll(PageRequest.of(pageNumber, size));
            return getUnSortedList(page);
        }
        Page<baseModel> page = getRepository().findAll(PageRequest.of(pageNumber, size, Sort.by(sort).descending()));
        return getSortedList(page);
    }

    private PageResult<ID, baseDto> getUnSortedList(Page<baseModel> page) {

        if (page.isEmpty()) {
            return new PageResult<>(true, ResponseKeys.EXCEPTION_RESPONSE,
                    ResponseKeys.EMPTY_LIST,
                    getBaseMapper().toBaseDtoList(page.getContent()),
                    page.getTotalElements(), page.getTotalPages());
        }
        return new PageResult<>(false, ResponseKeys.SUCCESS_RESPONSE,
                "unsorted filled list ",
                getBaseMapper().toBaseDtoList(page.getContent()),
                page.getTotalElements(),
                page.getTotalPages());
    }

    private PageResult<ID, baseDto> getSortedList(Page<baseModel> page) {

        if (page.isEmpty()) {
            return new PageResult<>(true, ResponseKeys.EXCEPTION_RESPONSE,
                    ResponseKeys.EMPTY_LIST,
                    getBaseMapper().toBaseDtoList(page.getContent()),
                    page.getTotalElements(),
                    page.getTotalPages());
        }
        return new PageResult<>(false, ResponseKeys.SUCCESS_RESPONSE,
                "sorted filled list ",
                getBaseMapper().toBaseDtoList(page.getContent()),
                page.getTotalElements(), page.getTotalPages());
    }


    public SingleResult<ID, baseDto> findById(ID modelId) {
        Optional<baseModel> optional = getRepository().findById(modelId);
        return optional
                .map(model -> new SingleResult<>(false, ResponseKeys.SUCCESS_RESPONSE,
                        ResponseKeys.OK,
                        getBaseMapper().toBaseDto(model)))
                .orElseGet(() -> new SingleResult<>(true, ResponseKeys.EXCEPTION_RESPONSE,
                        ResponseKeys.NOT_FOUND,
                        null));
    }

//    public SingleResultEntity<ID, baseModel> findById(ID modelId) {
//        Optional<baseModel> optional = getRepository().findById(modelId);
//        return optional.map(model -> new SingleResultEntity<>(false, "found the model", ResponseKeys.OK, model))
//                .orElseGet(() -> new SingleResultEntity<>(true, "model not found", ResponseKeys.NOT_FOUND, null));
//    }


    public SingleResult<ID, baseDto> delete(ID modelId) {
        Optional<baseModel> optional = getRepository().findById(modelId);
        return optional
                .map(model ->
                {
                    getRepository().delete(model);
                    return new SingleResult<>(false, ResponseKeys.SUCCESS_RESPONSE,
                            "found the model has been deleted successfully",
                            getBaseMapper().toBaseDto(model));
                })
                .orElseGet(() -> new SingleResult<>(false, ResponseKeys.EXCEPTION_RESPONSE,
                        "model not found to be deleted",
                        null));
    }
}
//new SingleResultDto<>(false, "found the model has been deleted successfully", ResponseKeys.OK, getBaseMapper().toBaseDto(model)
//        new SingleResultDto<>(false, "model not found to be deleted", ResponseKeys.NOT_FOUND, null)
