package org.example.springbootadventure.repository;

import java.util.List;
import org.example.springbootadventure.model.Book;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}




