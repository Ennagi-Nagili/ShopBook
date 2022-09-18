package com.example.test.repository;

import com.example.test.entity.BookEntity;
import com.example.test.entity.NewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Repository
public interface NewRepository extends JpaRepository<NewEntity, Integer> {
    Optional<NewEntity> findById(Integer id);
    Optional<NewEntity> findByName(String name);
}
