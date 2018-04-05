package com.example.demo2.repository;

import com.example.demo2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;




public interface userRepository extends JpaRepository<User,Long> {
     public User findByUsernameAndPassword(String username,String password);
     public User findByAuthNotIn(String Auth);
     public User findByUsername(String username);
     public User deleteByUsername(String username);

}
