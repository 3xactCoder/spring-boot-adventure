package org.example.springbootadventure.service;

import java.util.List;
import org.example.springbootadventure.model.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}


