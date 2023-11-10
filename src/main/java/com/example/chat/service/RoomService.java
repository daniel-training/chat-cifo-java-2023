package com.example.chat.service;


import com.example.chat.model.Room;
import com.example.chat.repository.RoomRepository;
import com.example.chat.util.Dummy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    // Dummy Rooms generation
    //static List<Room> rooms = Dummy.generateRooms(10,true);

    @Autowired
    RoomRepository roomRepository;


    public Iterable<Room> findAll() {
        return roomRepository.findAll();
    }

    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    public Optional<Room> findByUuid(String uuid) {
        return roomRepository.findByUuid(uuid);
    }

    public Optional<Room> findByTitle(String title) {
        return roomRepository.findByTitle(title);
    }

    public Room create(Room room) {
        return roomRepository.save(room);
    }

    public Room update(Long id, Room room) {
        Optional<Room> roomFound = findById(id);
        return roomFound.isPresent()
                ? roomRepository.save(room)
                : null;
    }

    public Room update(String uuid, Room room) {
        Optional<Room> roomFound = findByUuid(uuid);
        return roomFound.isPresent()
                ? roomRepository.save(room)
                : null;
    }

    public Room delete(Long id) {
        Optional<Room> roomFound = findById(id);
        Room roomDeleted = null;

        if (roomFound.isPresent()) {
            roomDeleted = roomFound.get();
            roomRepository.delete(roomDeleted);
        }

        return roomDeleted;
    }

    public Room delete(String uuid) {
        Optional<Room> roomFound = findByUuid(uuid);
        Room roomDeleted = null;

        if (roomFound.isPresent()) {
            roomDeleted = roomFound.get();
            roomRepository.delete(roomDeleted);
        }

        return roomDeleted;
    }


}
