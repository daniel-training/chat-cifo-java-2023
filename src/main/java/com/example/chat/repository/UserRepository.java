package com.example.chat.repository;

import com.example.chat.model.Room;
import com.example.chat.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUuid(String uuid);


}
