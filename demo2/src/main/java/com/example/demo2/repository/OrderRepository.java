package com.example.demo2.repository;

import com.example.demo2.domain.Order;
import com.example.demo2.domain.RoomAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    public List<Order> findByUsername(String username);
    public Order deleteByUsername(String username);
    public List<Order> findByInday(Date inday);
}
