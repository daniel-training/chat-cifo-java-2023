package com.example.chat.repository;

import com.example.chat.model.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, Long> {

    Optional<Room> findByUuid(String uuid);

    Optional<Room> findByTitle(String title);

}
