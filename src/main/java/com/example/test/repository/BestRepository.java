package com.example.test.repository;

import com.example.test.entity.BestEntity;
import com.example.test.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Repository
public interface BestRepository extends JpaRepository<BestEntity, Integer> {
    Optional<BestEntity> findById(Integer id);
    Optional<BestEntity> findByName(String Name);
}
