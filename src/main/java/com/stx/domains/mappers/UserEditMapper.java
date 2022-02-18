package com.stx.domains.mappers;

import com.stx.domains.dtos.CreateUserRequest;
import com.stx.domains.dtos.UpdateUserRequest;
import com.stx.domains.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserEditMapper {
    private final ModelMapper mapper;


    public UserEditMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public void update(UpdateUserRequest request, User user) {
        user.setPassword(request.getPassword());
        user.setUsername(request.getUsername());
    }

    public User create(CreateUserRequest request) {
        return mapper.map(request, User.class);
    }
}
