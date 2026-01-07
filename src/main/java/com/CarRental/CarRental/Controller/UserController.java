package com.CarRental.CarRental.Controller;

import com.CarRental.CarRental.Service.UserService;
import com.CarRental.CarRental.Models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok().body(user))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PostMapping("/create-many")
    public ResponseEntity<List<User>> createUsers(@RequestBody List<User> users) {
        List<User> savedUsers = userService.createUsers(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsers);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails)
                .map(updatedUser -> ResponseEntity.ok().body(updatedUser))
                .orElse(ResponseEntity.notFound().build());
    }



    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? "User with id " + id + " deleted successfully."
                : "User with id " + id + " not found.";
    }

}
