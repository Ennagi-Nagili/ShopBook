package com.example.test.repository;

import com.example.test.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, Integer> {
    Optional<BlogEntity> findById(Integer id);
    Optional<BlogEntity> findByHead(String head);
}
