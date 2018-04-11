package com.example.demo2.repository;

import com.example.demo2.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface historyRepository extends JpaRepository<History,Long> {
    public List<History> findByUsername(String username);
}
