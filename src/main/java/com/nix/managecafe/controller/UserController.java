package com.nix.managecafe.controller;

import com.nix.managecafe.exception.ResourceNotFoundException;
import com.nix.managecafe.model.User;
import com.nix.managecafe.payload.UserSummary;
import com.nix.managecafe.payload.request.SignUpRequest;
import com.nix.managecafe.payload.response.ApiResponse;
import com.nix.managecafe.repository.UserRepo;
import com.nix.managecafe.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers(1, 1);
    }
    @GetMapping("/{username}")
    public UserSummary getOne(@PathVariable("username") String username) {
        return userService.getOneUser(username);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        User user = userService.createUser(signUpRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(user.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Create user successfully"));
    }

    @PutMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserSummary updateUser(@PathVariable("username") String username,
                                  @Valid @RequestBody SignUpRequest updateRequest) {
        return userService.updateUser(username, updateRequest);
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByUsername(@PathVariable("username") String username
    ) {
        userService.removeByUsername(username);
    }
}
