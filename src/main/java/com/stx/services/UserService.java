package com.stx.services;

import com.stx.domains.daos.UserRepo;
import com.stx.domains.dtos.CreateUserRequest;
import com.stx.domains.dtos.LogoutUserRequest;
import com.stx.domains.dtos.UpdateUserRequest;
import com.stx.domains.dtos.UserDto;
import com.stx.domains.exceptions.NotFoundException;
import com.stx.domains.mappers.UserEditMapper;
import com.stx.domains.mappers.UserMapper;
import com.stx.domains.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final UserEditMapper userEditMapper;
    private final UserMapper userMapper;

    public UserService(UserRepo userRepo, PasswordEncoder encoder, UserEditMapper userEditMapper, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.userEditMapper = userEditMapper;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserDto create(CreateUserRequest request) throws ValidationException {
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new ValidationException("Пользователь существует!");
        }
        if (!request.getPassword().equals(request.getRePassword())) {
            throw new ValidationException("Пароли не совпадают!");
        }
        User user = userEditMapper.create(request);
        user.setPassword(encoder.encode(request.getPassword()));
        user = userRepo.save(user);
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserDto getUser(String username) {
        Optional<User> optionalUser = userRepo.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User is not fond");
        } else {
            User user = optionalUser.get();
            return userMapper.toDTO(user);
        }
    }

    @Transactional
    public UserDto upsert(CreateUserRequest request) throws ValidationException {
        Optional<User> optionalUser = userRepo.findByUsername(request.getUsername());

        if (optionalUser.isEmpty()) {
            return create(request);
        } else {
            UpdateUserRequest updateUserRequest = new UpdateUserRequest();
            updateUserRequest.setUsername(request.getUsername());
            updateUserRequest.setPassword(request.getPassword());
            return update(optionalUser.get().getId(), updateUserRequest);
        }
    }

    @Transactional
    public UserDto update(UUID id, UpdateUserRequest request) {
        User user = userRepo.getById(id);
        userEditMapper.update(request, user);
        user.setPassword(encoder.encode(request.getPassword()));
        user = userRepo.save(user);
        return userMapper.toDTO(user);
    }

    @Transactional
    public void deleteUser(UUID id) {
        userRepo.deleteById(id);
    }

    @Transactional
    public UserDto disableUser(UUID id) {
        User user = userRepo.getById(id);
        user.setEnabled(false);
        user = userRepo.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь \"%s\" не найден!"));
    }

    public UserDto logout(LogoutUserRequest request) {
        return disableUser(request.getId());
    }
}
