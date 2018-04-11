package com.example.demo2.repository;

import com.example.demo2.domain.History;
import com.example.demo2.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface historypageRepository extends PagingAndSortingRepository<History,Long> {
}
