package com.example.chat.service;


import com.example.chat.model.User;
import com.example.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUuid(String uuid) {
        return userRepository.findByUuid(uuid);
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(Long id, User user) {
        Optional<User> userFound = findById(id);
        return userFound.isPresent()
                ? userRepository.save(user)
                : null;
    }

    public User update(String uuid, User user) {
        Optional<User> userFound = findByUuid(uuid);
        return userFound.isPresent()
                ? userRepository.save(user)
                : null;
    }

    public User delete(Long id) {
        Optional<User> userFound = findById(id);
        User userDeleted = null;

        if (userFound.isPresent()) {
            userDeleted = userFound.get();
            userRepository.delete(userDeleted);
        }

        return userDeleted;
    }

    public User delete(String uuid) {
        Optional<User> userFound = findByUuid(uuid);
        User userDeleted = null;

        if (userFound.isPresent()) {
            userDeleted = userFound.get();
            userRepository.delete(userDeleted);
        }

        return userDeleted;
    }
    
    

}
