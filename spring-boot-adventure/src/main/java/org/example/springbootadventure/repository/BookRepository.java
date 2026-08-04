package org.example.springbootadventure.repository;

import org.example.springbootadventure.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {

}

