package com.stx.services;

import com.stx.daos.UserRepo;
import com.stx.domains.dtos.CreateUserRequest;
import com.stx.domains.dtos.UpdateUserRequest;
import com.stx.domains.dtos.UserView;
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

    public UserService(UserRepo userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @Transactional
    public UserView create(CreateUserRequest request) throws ValidationException {
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new ValidationException("Пользователь существует!");
        }
        if (!request.getPassword().equals(request.getRePassword())) {
            throw new ValidationException("Пароли не совпадают!");
        }
        return null;
    }

    @Transactional
    public UserView upsert(CreateUserRequest request) throws ValidationException {
        Optional<User> optionalUser = userRepo.findByUsername(request.getUsername());

        if (optionalUser.isEmpty()) {
            return create(request);
        } else {
            UpdateUserRequest updateUserRequest = new UpdateUserRequest();
            return update(optionalUser.get().getId(), updateUserRequest);
        }
    }

    @Transactional
    public UserView update(UUID id, UpdateUserRequest request) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь \"%s\" не найден!"));
    }
}
