package com.example.demo2.repository;

import com.example.demo2.domain.Juser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JuserRepository extends JpaRepository<Juser,Long> {
    public Juser findByUsername(String username);
}
