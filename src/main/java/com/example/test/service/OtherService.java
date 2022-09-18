package com.example.test.service;

import com.example.test.entity.BestEntity;
import com.example.test.entity.BlogEntity;
import com.example.test.entity.BookEntity;
import com.example.test.entity.NewEntity;
import com.example.test.repository.BestRepository;
import com.example.test.repository.BlogRepository;
import com.example.test.repository.BookRepository;
import com.example.test.repository.NewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OtherService {
    private BestRepository bestRepository;
    private NewRepository newRepository;
    private BookRepository bookRepository;
    private BlogRepository blogRepository;

    public OtherService(BestRepository bestRepository, NewRepository newRepository, BookRepository bookRepository, BlogRepository blogRepository) {
        this.bestRepository = bestRepository;
        this.newRepository = newRepository;
        this.bookRepository = bookRepository;
        this.blogRepository = blogRepository;
    }

    public List<BestEntity> getBestList() {
        List<BestEntity> best = bestRepository.findAll();
        return best;
    }

    public List<NewEntity> getNewList() {
        List<NewEntity> newEntityList = newRepository.findAll();
        return newEntityList;
    }

    public List<BlogEntity> getBlogList() {
        List<BlogEntity> blogList = blogRepository.findAll();
        return blogList;
    }

    public BestEntity getBest(String name) {
        Optional<BestEntity> optionalBook = bestRepository.findByName(name);
        var bestEntity = optionalBook.orElseGet(BestEntity::new);
        return bestEntity;
    }

    public NewEntity getNew(String name) {
        Optional<NewEntity> optionalBook = newRepository.findByName(name);
        var newEntity = optionalBook.orElseGet(NewEntity::new);
        return newEntity;
    }

    public BlogEntity getBlog(String name) {
        Optional<BlogEntity> optionalBook = blogRepository.findByHead(name);
        var blogEntity = optionalBook.orElseGet(BlogEntity::new);
        return blogEntity;
    }

    public void bestAdd(BestEntity bestEntity) {
        bestRepository.save(bestEntity);
    }

    public void newAdd(NewEntity newEntity) {
       newRepository.save(newEntity);
    }

    public void blogAdd(BlogEntity blogEntity) {
        blogRepository.save(blogEntity);
    }

    public void deleteBest(Integer id) {
        bestRepository.deleteById(id);
    }

    public void deleteNew(Integer id) {
        newRepository.deleteById(id);
    }

    public void deleteBlog(Integer id) {
        blogRepository.deleteById(id);
    }

    public void bestEdit(Integer id, BestEntity bookEntity) {
        var existingBook = bestRepository.findById(id);
        bookEntity.setId(existingBook.get().getId());
        bestRepository.save(bookEntity);
    }

    public void newEdit(Integer id, NewEntity bookEntity) {
        var existingBook = newRepository.findById(id);
        bookEntity.setId(existingBook.get().getId());
        newRepository.save(bookEntity);
    }

    public void blogEdit(Integer id, BlogEntity bookEntity) {
        var existingBook = blogRepository.findById(id);
        bookEntity.setId(existingBook.get().getId());
        blogRepository.save(bookEntity);
    }
}
