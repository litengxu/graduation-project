package com.example.demo2.repository;

import com.example.demo2.domain.Room;
import com.example.demo2.domain.RoomAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface RoomAdminRepository extends JpaRepository<RoomAdmin,Long>{
    public List<RoomAdmin> findByUsername(String username);
    public RoomAdmin deleteByUsername(String username);
    public List<RoomAdmin> findByInday(Date inday);
   // public RoomAdmin findById(Long id);

}
