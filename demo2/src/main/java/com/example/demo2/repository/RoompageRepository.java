package com.example.demo2.repository;

import com.example.demo2.domain.Room;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoompageRepository extends PagingAndSortingRepository<Room,Long> {

}
