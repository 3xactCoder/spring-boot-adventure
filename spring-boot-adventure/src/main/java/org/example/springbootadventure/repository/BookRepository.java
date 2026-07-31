package org.example.springbootadventure.repository;

import java.util.List;
import java.util.Optional;

import org.example.springbootadventure.model.Book;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();

    Optional<Book> findById(Long id);
}

