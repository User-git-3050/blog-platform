package az.msblog.controller;

import az.msblog.dao.request.AuthRequest;
import az.msblog.dao.response.JwtResponse;
import az.msblog.dao.request.UserRequest;
import az.msblog.entity.UserEntity;
import az.msblog.security.JwtUtil;
import az.msblog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static az.msblog.enums.ErrorMessages.INVALID_USER_REQUEST;
import static az.msblog.enums.ErrorMessages.USER_ALREADY_EXISTS;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public JwtResponse AuthenticateAndGetToken(@RequestBody @Valid AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return JwtResponse.builder()
                    .accessToken(jwtUtil.generateToken(authRequest.getUsername())).build();
        } else {
            throw new UsernameNotFoundException(INVALID_USER_REQUEST.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registration(@RequestBody @Valid UserRequest userRequest) {
        UserEntity existingUser = userService.findUserByEmail(userRequest.getEmail());
        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            return ResponseEntity.ok(USER_ALREADY_EXISTS.getMessage());
        } else {
            userService.saveUser(userRequest);
            return ResponseEntity.ok("User registered successfully");
        }
    }


}
