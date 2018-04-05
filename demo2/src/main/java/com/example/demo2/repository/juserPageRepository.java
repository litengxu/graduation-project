package com.example.demo2.repository;

import com.example.demo2.domain.Juser;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface juserPageRepository extends PagingAndSortingRepository<Juser,Long> {
}
