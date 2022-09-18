package com.example.test.repository;

import com.example.test.entity.BookEntity;
import com.example.test.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {
    Optional<BookEntity> findById(Integer id);
    BookEntity findByName(String name);
}
