package com.nix.managecafe.service;

import com.nix.managecafe.exception.AppException;
import com.nix.managecafe.exception.BadRequestException;
import com.nix.managecafe.exception.ResourceNotFoundException;
import com.nix.managecafe.model.Role;
import com.nix.managecafe.model.enumname.RoleName;
import com.nix.managecafe.model.User;
import com.nix.managecafe.payload.UserSummary;
import com.nix.managecafe.payload.request.SignUpRequest;
import com.nix.managecafe.repository.RoleRepo;
import com.nix.managecafe.repository.UserRepo;
import com.nix.managecafe.util.ValidatePageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(SignUpRequest signUpRequest) {
        if (userRepo.existsByUsername(signUpRequest.getUsername())) {
            throw new BadRequestException("Username already in use!!!");
        }

        if (userRepo.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email Address already in use!!!");
        }

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        Role userRole = roleRepo.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(userRole));

        return userRepo.save(user);
    }


    public void removeByUsername(String username){
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        userRepo.delete(user);
    }

    public UserSummary updateUser(String username, SignUpRequest updateRequest) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
        user.setName(updateRequest.getName());
        user.setUsername(updateRequest.getUsername());

        User updatedUser = userRepo.save(user);


        return new UserSummary(updatedUser.getId(), updatedUser.getUsername(), updatedUser.getName(), updatedUser.getEmail());


    }

    public List<User> getAllUsers(int page, int size) {
        ValidatePageable.invoke(page, size);

        List<User> users = userRepo.findAll();

        return users;
    }

    public UserSummary getOneUser(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        return new UserSummary(user.getId(), user.getUsername(), user.getName(), user.getEmail());
    }
}
