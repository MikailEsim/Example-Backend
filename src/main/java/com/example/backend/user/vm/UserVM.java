package com.example.backend.user.vm;

import com.example.backend.user.User;
import lombok.Data;

@Data
public class UserVM {

    private String username;

    private String displayName;

    private String image;

    public UserVM(User user) {
        this.setUsername(user.getUsername());
        this.setDisplayName(user.getDisplayName());
        this.setImage(user.getImage());
    }

}
