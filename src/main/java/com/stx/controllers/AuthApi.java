package com.stx.controllers;

import com.stx.config.security.JwtUtil;
import com.stx.domains.dtos.AuthUserRequest;
import com.stx.domains.dtos.CreateUserRequest;
import com.stx.domains.dtos.LogoutUserRequest;
import com.stx.domains.dtos.UserDto;
import com.stx.domains.mappers.UserViewMapper;
import com.stx.domains.models.User;
import com.stx.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;

@RestController
@RequestMapping("api/public")
public class AuthApi {
    private final Logger LOGGER = Logger.getLogger(getClass());

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserViewMapper userViewMapper;
    private final JwtUtil jwtUtil;

    public AuthApi(AuthenticationManager authenticationManager, UserService userService, UserViewMapper userViewMapper, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userViewMapper = userViewMapper;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody @Validated AuthUserRequest request) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            User user = (User) authenticate.getPrincipal();
            LOGGER.info("AUTHORIZATION: " + user.getUsername());
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtUtil.generateToken(user))
                    .body(userViewMapper.toDTO(user));
        } catch (BadCredentialsException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("signIn")
    public ResponseEntity<?> signIn(@RequestBody @Validated CreateUserRequest request) {
        try {
            UserDto UserDto = userService.upsert(request);
            LOGGER.info("CREATE user: " + UserDto.getUsername());
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION)
                    .body(UserDto);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(@RequestBody @Validated LogoutUserRequest request) {
        try {
            UserDto dto = userService.logout(request);
            return ResponseEntity.ok()
                    .header(HttpHeaders.UPGRADE)
                    .body(dto);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(e.getMessage());
        }
    }
}
