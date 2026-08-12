package org.example.springbootadventure.service;

import org.example.springbootadventure.dto.BookDto;
import org.example.springbootadventure.dto.BookSearchParametersDto;
import org.example.springbootadventure.dto.CreateBookRequestDto;
import org.springframework.data.domain.Page;


import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto createBook(CreateBookRequestDto requestDto);

    Page<BookDto> getAll(Pageable pageable);

    BookDto getBookById(Long id);

    public BookDto updateBook(Long id, CreateBookRequestDto requestDto);

    public void deleteById(Long id);

    public Page<BookDto> search(BookSearchParametersDto params,Pageable pageable);
}