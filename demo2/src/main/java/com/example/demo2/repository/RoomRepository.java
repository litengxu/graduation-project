package com.example.demo2.repository;

import com.example.demo2.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Long> {

    public Room findByType(String Type);



}
