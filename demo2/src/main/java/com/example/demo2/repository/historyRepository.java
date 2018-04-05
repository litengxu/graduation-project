package com.example.demo2.repository;

import com.example.demo2.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface historyRepository extends JpaRepository<History,Long> {
    public History findByUsername(String username);
}
