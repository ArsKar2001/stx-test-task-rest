package com.stx.domains.mappers;

import com.stx.domains.dtos.UserDto;
import com.stx.domains.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements ModelDTOMapper<User, UserDto> {
    private final ModelMapper mapper;

    public UserMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public UserDto toDTO(User t) {
        return mapper.map(t, UserDto.class);
    }

    @Override
    public User toModel(UserDto f) {
        return mapper.map(f, User.class);
    }
}
