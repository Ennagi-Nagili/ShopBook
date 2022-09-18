package com.example.test.repository;

import com.example.test.entity.BookEntity;
import com.example.test.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Integer> {
    Optional<CartEntity> findById(Integer id);
}
