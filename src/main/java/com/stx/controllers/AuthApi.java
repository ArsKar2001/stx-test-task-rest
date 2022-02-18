package com.stx.controllers;

import com.stx.domains.dtos.AuthUserRequest;
import com.stx.domains.dtos.CreateUserRequest;
import com.stx.domains.dtos.UserView;
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

    public AuthApi(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<UserView> login(@RequestBody @Validated AuthUserRequest request) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
                    );
            User user = (User) authenticate.getPrincipal();
            LOGGER.info("AUTHORIZATION: " + user.getUsername());
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION)
                    .body(null);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("signIn")
    public ResponseEntity<UserView> signIn(@RequestBody @Validated CreateUserRequest request) {
        try {
            UserView view = userService.create(request);
            LOGGER.info("CREATE user: " + view.getUsername());
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION)
                    .body(view);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
