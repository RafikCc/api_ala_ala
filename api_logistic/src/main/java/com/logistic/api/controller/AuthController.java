package com.logistic.api.controller;

import com.logistic.api.model.User;
import com.logistic.api.payload.request.AuthRequest;
import com.logistic.api.payload.response.AuthResponse;
import com.logistic.api.service.UserService;
import com.logistic.api.util.JwtTokenUtil;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation(value = "get login token", response = AuthResponse.class)
    @PostMapping(value = "/login", produces = {"application/json"})
    public ResponseEntity<?> login(
            @ApiParam(value = "Username and Password", required = true)
            @RequestBody(required = true) AuthRequest request) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (AccountExpiredException e) {
            throw new AccountExpiredException("USET_ACCOUNT_HAS_EXPIRED");
        } catch (LockedException e) {
            throw new LockedException("USER_LOCKED");
        } catch (DisabledException e) {
            throw new DisabledException("USER_DISABLED");
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS");
        } catch (InternalAuthenticationServiceException e) {
            throw new NullPointerException("USER_NOT_FOUND");
        }

        UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        headers.add("Authorization", "Bearer " + token);
        userService.updateTokenUser(token, request.getUsername());

        return new ResponseEntity<AuthResponse>(new AuthResponse(token), headers, HttpStatus.OK);
    }

    @ApiOperation(value = "insert new user", response = String.class)
    @PostMapping(value = "/signup", produces = {"application/json"})
    public ResponseEntity<?> signup(
            @ApiParam(value = "username and password", required = true)
            @RequestBody(required = true) AuthRequest request) {
        if (Objects.isNull(request.getUsername())) {
            return ResponseEntity.badRequest().body("{\"message\" : \"username must filled.\"}");
        }

        if (Objects.isNull(request.getPassword())) {
            return ResponseEntity.badRequest().body("{\"message\" : \"password must filled.\"}");
        }

        if (userService.isUserExist(request.getUsername())) {
            return ResponseEntity.badRequest().body("{\"message\" : \"username already exist.\"}");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        try {
            userService.saveUser(user);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\" : \"error insert new user.\"}");
        }

        return ResponseEntity.ok().body("{\"message\" : \"user already inserted.\"}");
    }
}
