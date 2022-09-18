package com.example.test.service;

import com.example.test.entity.BookEntity;
import com.example.test.entity.CartEntity;
import com.example.test.mapper.AuthorityMapper;
import com.example.test.mapper.BookMapper;
import com.example.test.mapper.CartMapper;
import com.example.test.mapper.UserMapper;
import com.example.test.model.Authority;
import com.example.test.model.Cart;
import com.example.test.model.Product;
import com.example.test.model.User;
import com.example.test.repository.AuthorityRepository;
import com.example.test.repository.BookRepository;
import com.example.test.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;

    public BookService(BookRepository bookRepository, UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    public Product getProduct(Integer id) {
        Optional<BookEntity> optionalBook = bookRepository.findById(id);
        var bookEntity = optionalBook.orElseGet(BookEntity::new);
        var productDto = BookMapper.INSTANCE.entityDto(bookEntity);
        return productDto;
    }

    public BookEntity getEntity(Integer id) {
        Optional<BookEntity> optionalBook = bookRepository.findById(id);
        var bookEntity = optionalBook.orElseGet(BookEntity::new);
        return bookEntity;
    }

    public void register(User user, Authority authority) {
        userRepository.save(UserMapper.INSTANCE.DtoToEntity(user));
        authorityRepository.save(AuthorityMapper.INSTANCE.DtoToEntity(authority));
    }

    public void save(Authority authority) {
        authorityRepository.save(AuthorityMapper.INSTANCE.DtoToEntity(authority));
    }

    public void book(Product product) {
        bookRepository.save(BookMapper.INSTANCE.DtoToEntity(product));
    }

    public List<BookEntity> getBookList() {
        List<BookEntity> book = bookRepository.findAll();
        return book;
    }

    public List<BookEntity> getBook(String name) {
        List<BookEntity> book = bookRepository.findAll();
        List<BookEntity> find = new ArrayList<>();

        for (int i = 0; i < book.size(); i++) {
            if (book.get(i).getName().toLowerCase().contains(name.toLowerCase())) {
                find.add(book.get(i));
            }
        }

        return find;
    }

    public void deleteById(Integer id) {
        bookRepository.deleteById(id);
    }

    public void edit(Integer id, BookEntity bookEntity) {
        var existingBook = bookRepository.findById(id);
        bookEntity.setId(existingBook.get().getId());
        bookRepository.save(bookEntity);
    }
}
