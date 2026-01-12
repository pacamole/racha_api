package com.muller.racha_api.controller;

import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muller.racha_api.controller.docs.UserControllerDocs;
import com.muller.racha_api.dto.UpdateUserDTO;
import com.muller.racha_api.model.User;
import com.muller.racha_api.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController implements UserControllerDocs {
    private final UserService userService;

    @GetMapping
    public Page<User> findAllUsers(
            @ParameterObject @PageableDefault(size = 10, page = 0, sort = "createdAt") Pageable pageable) {
        return userService.findAllUsers(pageable);
    }

    @GetMapping("/{userId}")
    public User findUserById(@PathVariable String userId) {
        return userService.findUserById(UUID.fromString(userId));

    }

    @PutMapping("/{userId}")
    public User update(@PathVariable String userId, @RequestBody UpdateUserDTO dto) {
        return userService.update(UUID.fromString(userId), dto);
    }

    @PutMapping("/me")
    public User update(@AuthenticationPrincipal User user, @RequestBody UpdateUserDTO dto) {
        return userService.update(user.getId(), dto);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable String userId) {
        userService.delete(UUID.fromString(userId));
    }

    @DeleteMapping("/me")
    public void delete(@AuthenticationPrincipal User user) {
        userService.delete(user.getId());
    }

}
