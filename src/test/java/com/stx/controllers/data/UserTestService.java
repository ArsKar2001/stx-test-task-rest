package com.stx.controllers.data;

import com.stx.domains.dtos.CreateUserRequest;
import com.stx.domains.dtos.UserDto;
import com.stx.services.UserService;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;

@Service
public class UserTestService {
    private final UserService userService;

    public UserTestService(UserService userService) {
        this.userService = userService;
    }

    public UserDto createUser(String username, String password) throws ValidationException {
        CreateUserRequest request = new CreateUserRequest();
        request.setPassword(password);
        request.setUsername(username);
        request.setRePassword(password);

        UserDto dto = userService.upsert(request);

        assertNotNull(dto.getId().toString(), "User id must not be null");
        return dto;
    }

    public UserDto getUser(String username) {
        UserDto dto = userService.getUser(username);
        assertNotNull(dto.getId().toString(), "User id must not be null");
        return dto;
    }

    private void deleteUser(UUID id) {
        userService.deleteUser(id);
    }

    public void deleteUser(String id) {
        UUID uuid = UUID.fromString(id);
        deleteUser(uuid);
    }
}
