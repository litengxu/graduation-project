package com.example.demo2.repository;

import com.example.demo2.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface userPageRepository extends PagingAndSortingRepository<User,Long> {
}
