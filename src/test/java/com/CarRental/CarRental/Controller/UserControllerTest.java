package com.CarRental.CarRental.Controller;

import com.CarRental.CarRental.Models.User;
import com.CarRental.CarRental.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService; // <--- Spring now injects this mock into the controller

    private ObjectMapper objectMapper = new ObjectMapper();

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setId(1L);
        user1.setFirstName("Alice");
        user1.setLastName("Smith");

        user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Bob");
        user2.setLastName("Johnson");
    }

    @Test
    void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("Alice"))
                .andExpect(jsonPath("$[0].lastName").value("Smith"))
                .andExpect(jsonPath("$[1].firstName").value("Bob"))
                .andExpect(jsonPath("$[1].lastName").value("Johnson"));
    }

    @Test
    void testGetUserByIdFound() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.of(user1));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.lastName").value("Smith"));
    }

    @Test
    void testGetUserByIdNotFound() throws Exception {
        when(userService.getUserById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateUser() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(user1);

        mockMvc.perform(post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.lastName").value("Smith"));
    }

    @Test
    void testCreateUsers() throws Exception {
        List<User> usersToCreate = List.of(user1, user2);
        when(userService.createUsers(any())).thenReturn(usersToCreate);

        mockMvc.perform(post("/users/create-many")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usersToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("Alice"))
                .andExpect(jsonPath("$[0].lastName").value("Smith"))
                .andExpect(jsonPath("$[1].firstName").value("Bob"))
                .andExpect(jsonPath("$[1].lastName").value("Johnson"));
    }

    @Test
    void testUpdateUserSuccess() throws Exception {
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setFirstName("AliceUpdated");
        updatedUser.setLastName("SmithUpdated");

        when(userService.updateUser(any(Long.class), any(User.class))).thenReturn(Optional.of(updatedUser));

        mockMvc.perform(put("/users/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("AliceUpdated"))
                .andExpect(jsonPath("$.lastName").value("SmithUpdated"));
    }

    @Test
    void testUpdateUserNotFound() throws Exception {
        when(userService.updateUser(any(Long.class), any(User.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/users/update/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteUserSuccess() throws Exception {
        when(userService.deleteUser(1L)).thenReturn(true);

        mockMvc.perform(delete("/users/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User with id 1 deleted successfully."));
    }

    @Test
    void testDeleteUserNotFound() throws Exception {
        when(userService.deleteUser(99L)).thenReturn(false);

        mockMvc.perform(delete("/users/delete/99"))
                .andExpect(status().isOk())
                .andExpect(content().string("User with id 99 not found."));
    }
}
