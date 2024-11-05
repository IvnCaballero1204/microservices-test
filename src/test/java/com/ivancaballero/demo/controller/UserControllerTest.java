package com.ivancaballero.demo.controller;

import com.ivancaballero.demo.exception.UserNotFoundException;
import com.ivancaballero.demo.model.User;
import com.ivancaballero.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("Ivan Caballero");
        mockUser.setEmial("test@emial.com");
        mockUser.setPassword("password123");
    }

    @Test
    void testCreateUser_Succes() {
        when(userService.createUser(any(User.class))).thenReturn(mockUser);

        ResponseEntity<Object> response = userController.createUser(mockUser);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User is created succesfully with id=1", response.getBody());
        verify(userService, times(1)).createUser(mockUser);
    }

    @Test
    void testUpdateUser_Success() {
        when(userService.isUserExist(1L)).thenReturn(true);
        when(userService.createUser(any(User.class))).thenReturn(mockUser);

        ResponseEntity<Object> response = userController.updateUser(1L, mockUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User updated successfully id=1", response.getBody());
        verify(userService, times(1)).isUserExist(1L);
        verify(userService, times(1)).createUser(mockUser);
    }

    @Test
    void testUpdateUser_UserNotFound() {
        when(userService.isUserExist(1L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userController.updateUser(1L, mockUser));
        verify(userService, times(1)).isUserExist(1L);
        verify(userService, never()).createUser(any(User.class));
    }

    @Test
    void testUpdatePartial_Success() {
        when(userService.isUserExist(1L)).thenReturn(true);
        when(userService.updateUser(anyLong(), any(Map.class))).thenReturn(mockUser);

        Map<String, Object> update = new HashMap<>();
        update.put("username", "Name test");

        ResponseEntity<Object> response = userController.partialUpdate(1L, update);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User updated succesfully id=1", response.getBody());
        verify(userService, times(1)).isUserExist(1L);
        verify(userService, times(1)).updateUser(1L, update);
    }

    @Test
    void testGetUsers_Success() {
        List<User> users = Collections.singletonList(mockUser);
        when(userService.getUsers()).thenReturn(users);
        ResponseEntity<Object> response = userController.getUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
        verify(userService, times(1)).getUsers();
    }

    @Test
    void testGetUserById_Success() {
        when(userService.isUserExist(1L)).thenReturn(true);
        when(userService.getUserById(1L)).thenReturn(mockUser);

        ResponseEntity<Object> response = userController.getUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody());
        verify(userService, times(1)).isUserExist(1L);
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void tesGetUserById_UserNotFound() {
        when(userService.isUserExist(1L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userController.getUser(1L));
        verify(userService, times(1)).isUserExist(1L);
        verify(userService, never()).getUserById(anyLong());
    }

    @Test
    void testDeleteUser_Success() {
        when(userService.isUserExist(1L)).thenReturn(true);

        ResponseEntity<Object> response = userController.deleteUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted succesfully", response.getBody());
    }

    @Test
    void testDeleteUser_UserNotFound() {
        when(userService.isUserExist(1L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userController.deleteUser(1L));
        verify(userService, times(1)).isUserExist(1L);
        verify(userService, never()).deleteUser(anyLong());
    }
}
