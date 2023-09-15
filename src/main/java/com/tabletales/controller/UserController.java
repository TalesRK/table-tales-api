package com.tabletales.controller;

import com.tabletales.entity.User;
import com.tabletales.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{userId}")
    public User updateUser(
        @PathVariable UUID userId,
        @RequestBody User user
    ) {
        var oldUser = userRepository.findById(userId).orElseThrow();
        oldUser.setEmail(user.getEmail());
        oldUser.setName(user.getName());
        return userRepository.save(oldUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable UUID userId) {
        userRepository.deleteById(userId);
    }

}
