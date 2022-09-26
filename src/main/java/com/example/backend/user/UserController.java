package com.example.backend.user;

import com.example.backend.shared.CurrentUser;
import com.example.backend.shared.GenericResponse;
import com.example.backend.user.vm.UserUpdateVM;
import com.example.backend.user.vm.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public GenericResponse createUser(@Valid @RequestBody User user) {
        userService.save(user);
        return new GenericResponse("user created");
    }

    @GetMapping("/users/list")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserVM> getUsers(Pageable pageable, @CurrentUser User user) {
        return userService.getUsers(pageable, user).map(UserVM::new);
    }

    @GetMapping("/profile/{username}")
    public UserVM getUserProfile(@PathVariable String username) {
        User user = userService.getProfileByUsername(username);
        return new UserVM(user);
    }

    @PutMapping("/profile/update/{username}")
    @PreAuthorize("#username == principal.username")
    public UserVM updateUser(@PathVariable String username, @RequestBody UserUpdateVM userUpdateVM) {
        User user = userService.UpdateUser(username, userUpdateVM);
        return new UserVM(user);
    }

}
