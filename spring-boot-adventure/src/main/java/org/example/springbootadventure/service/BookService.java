package org.example.springbootadventure.service;

import org.example.springbootadventure.dto.BookDto;
import org.example.springbootadventure.dto.CreateBookRequestDto;

import java.util.List;

public interface BookService {
    BookDto createBook(CreateBookRequestDto requestDto);

    List<BookDto> getAll();

    BookDto getBookById(Long id);

    public BookDto updateBook(Long id, CreateBookRequestDto requestDto);

    public void deleteById(Long id);
}