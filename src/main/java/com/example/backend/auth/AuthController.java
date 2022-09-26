package com.example.backend.auth;

import com.example.backend.shared.CurrentUser;
import com.example.backend.user.User;
import com.example.backend.user.vm.UserVM;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @PostMapping("/signin")
    UserVM handleAuthentication(@CurrentUser User user) {
        return new UserVM(user);
    }

}
