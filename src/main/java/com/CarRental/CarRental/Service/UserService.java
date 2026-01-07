package com.CarRental.CarRental.Service;

import com.CarRental.CarRental.Models.User;
import com.CarRental.CarRental.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }

    public User createUser(User user) {
        return userRepo.save(user);
    }

    public List<User> createUsers(List<User> users) {
        return userRepo.saveAll(users);
    }


    public Optional<User> updateUser(Long id, User userDetails) {
        return userRepo.findById(id).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            user.setRole(userDetails.getRole());
            user.setFirstName(userDetails.getFirstName());
            user.setLastName(userDetails.getLastName());
            user.setAge(userDetails.getAge());
            return userRepo.save(user);
        });
    }


    public boolean deleteUser(Long id) {
        return userRepo.findById(id).map(user -> {
            userRepo.delete(user);
            return true;
        }).orElse(false);
    }
}
