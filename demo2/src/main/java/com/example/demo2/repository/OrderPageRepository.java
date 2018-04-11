package com.example.demo2.repository;

import com.example.demo2.domain.Order;
import com.example.demo2.domain.RoomAdmin;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderPageRepository  extends PagingAndSortingRepository<Order,Long> {
}
