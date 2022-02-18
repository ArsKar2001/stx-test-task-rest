package com.stx.domains.mappers;

import com.stx.domains.dtos.UserDTO;
import com.stx.domains.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserViewMapper implements ModelDTOMapper<User, UserDTO> {
    private final ModelMapper mapper;

    public UserViewMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public UserDTO toDTO(User t) {
        return mapper.map(t, UserDTO.class);
    }

    @Override
    public User toModel(UserDTO f) {
        return mapper.map(f, User.class);
    }
}
