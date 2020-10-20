package com.skeleton.mapper;


import com.skeleton.dto.BaseDto;
import com.skeleton.model.BaseModel;

import java.io.Serializable;
import java.util.List;

public interface BaseMapper<ID extends Serializable, baseDto extends BaseDto<ID>, baseModel extends BaseModel<ID>> {

    baseDto toBaseDto(baseModel baseModelPram);

    baseModel toBaseEntity(baseDto baseDtoPram);

    List<baseDto> toBaseDtoList(List<baseModel> baseModelList);

    List<baseModel> toBaseEntityList(List<baseDto> baseDtoList);

}
