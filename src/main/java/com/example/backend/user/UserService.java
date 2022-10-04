package com.example.backend.user;

import com.example.backend.error.NotFoundException;
import com.example.backend.file.FileService;
import com.example.backend.user.vm.UserUpdateVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class UserService {

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    FileService fileService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, FileService fileService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileService = fileService;
    }

    public void save(User user) {
        String encryptedPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }

    public Page<User> getUsers(Pageable pageable, User user) {
        if (user != null) {
            return userRepository.findByUsernameNot(user.getUsername(), pageable);
        }
        return userRepository.findAll(pageable);
    }

    public User getProfileByUsername(String username) {
        User inDB = userRepository.findByUsername(username);
        if (inDB == null) {
            throw new NotFoundException();
        }
        return inDB;
    }

    public User UpdateUser(String username, UserUpdateVM userUpdateVM) {
        User inDB = getProfileByUsername(username);
        inDB.setDisplayName(userUpdateVM.getDisplayName());
        if (userUpdateVM.getImage() != null) {
            String oldImageName = inDB.getImage();
            try {
                String storedFileName = fileService.writeBase64EncodedStringToFile(userUpdateVM.getImage());
                inDB.setImage(storedFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileService.deleteFile(oldImageName);
        }
        return userRepository.save(inDB);
    }

}
