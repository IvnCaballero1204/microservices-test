package com.ivancaballero.demo.controller;

import com.ivancaballero.demo.exception.UserNotFoundException;
import com.ivancaballero.demo.model.User;
import com.ivancaballero.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userSerice;

    @Autowired
    public UserController(UserService userService) {
        this.userSerice = userService;
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        User userCreated = this.userSerice.createUser(user);
        return new ResponseEntity<>("User is created succesfully with id=" + userCreated.getId(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> getUsers() {
        List<User> users = this.userSerice.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Long id) {
        boolean isUserExist = this.userSerice.isUserExist(id);

        if (isUserExist) {
            User user = this.userSerice.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            throw new UserNotFoundException();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody User update) {
        boolean isUserExist = this.userSerice.isUserExist(id);

        if (isUserExist) {
            update.setId(id);
            User user = this.userSerice.createUser(update);
            return new ResponseEntity<>("User updated successfully id=" + user.getId(), HttpStatus.OK);
        } else {
            throw new UserNotFoundException();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> update) {
        boolean isUserExist = this.userSerice.isUserExist(id);

        if (isUserExist) {
            User updatedUser = this.userSerice.updateUser(id, update);
            return new ResponseEntity<>("User updated succesfully id=" + updatedUser.getId(), HttpStatus.OK);
        } else {
            throw new UserNotFoundException();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        boolean isUserExist = this.userSerice.isUserExist(id);

        if (isUserExist) {
            this.userSerice.deleteUser(id);
            return new ResponseEntity<>("User deleted succesfully", HttpStatus.OK);
        } else {
            throw new UserNotFoundException();
        }
    }
}
