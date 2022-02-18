package com.stx.controllers;

import com.stx.domains.dtos.*;
import com.stx.domains.mappers.UserViewMapper;
import com.stx.domains.models.User;
import com.stx.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.UUID;

@RestController
@RequestMapping("api/public")
public class AuthApi {
    private final Logger LOGGER = Logger.getLogger(getClass());

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserViewMapper userViewMapper;

    public AuthApi(AuthenticationManager authenticationManager, UserService userService, UserViewMapper userViewMapper) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userViewMapper = userViewMapper;
    }

    @PostMapping("login")
    public ResponseEntity<UserDTO> login(@RequestBody @Validated AuthUserRequest request) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
                    );
            User user = (User) authenticate.getPrincipal();
            LOGGER.info("AUTHORIZATION: " + user.getUsername());
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION)
                    .body(userViewMapper.toDTO(user));
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("signIn")
    public ResponseEntity<UserDTO> signIn(@RequestBody @Validated CreateUserRequest request) {
        try {
            UserDTO userDTO = userService.create(request);
            LOGGER.info("CREATE user: " + userDTO.getUsername());
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION)
                    .body(userDTO);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("logout")
    public ResponseEntity<UserDTO> logout(@RequestBody @Validated LogoutUserRequest request) {
        try {
            UserDTO dto = userService.logout(request);
            return ResponseEntity.ok()
                    .header(HttpHeaders.UPGRADE)
                    .body(dto);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }
}
