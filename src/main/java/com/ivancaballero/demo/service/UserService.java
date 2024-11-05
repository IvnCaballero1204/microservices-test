package com.ivancaballero.demo.service;

import com.ivancaballero.demo.model.User;
import com.ivancaballero.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return this.userRepository.save(user);
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User getUserById(Long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        return userOptional.get();
    }

    public boolean isUserExist(Long id) {
        return this.userRepository.existsById(id);
    }

    public User updateUser(Long id, Map<String, Object> update) {
        Optional<User> userOptional = this.userRepository.findById(id);
        User user = userOptional.get();

        update.forEach((field, value) -> {
            switch (field) {
                case "username":
                    user.setUsername((String) value);
                    break;
                case "emial":
                    user.setEmial((String) value);
                    break;
                case "password":
                    user.setPassword((String) value);
                    break;
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid field " + field);
            }
        });

        return this.userRepository.save(user);
    }

    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }
}
