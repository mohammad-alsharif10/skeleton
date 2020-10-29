package com.skeleton.mapper;


import com.skeleton.dto.UserDto;
import com.skeleton.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<Long, UserDto, User> {

    @Override
    @Mappings({
            @Mapping(target = "password", ignore = true)
    })
    UserDto toBaseDto(User baseModelPram);

    @Override
    User toBaseEntity(UserDto baseDtoPram);

    @Override
    List<UserDto> toBaseDtoList(List<User> users);

    @Override
    List<User> toBaseEntityList(List<UserDto> userDtos);
}
