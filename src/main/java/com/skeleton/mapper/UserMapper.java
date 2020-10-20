package com.skeleton.mapper;


import com.skeleton.dto.UserDto;
import com.skeleton.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<Long, UserDto, User> {

    @Override
    UserDto toBaseDto(User baseModelPram);

    @Override
    User toBaseEntity(UserDto baseDtoPram);

    @Override
    List<UserDto> toBaseDtoList(List<User> users);

    @Override
    List<User> toBaseEntityList(List<UserDto> userDtos);
}
