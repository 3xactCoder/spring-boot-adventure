package org.example.springbootadventure.service.book;

import org.example.springbootadventure.dto.book.BookDto;
import org.example.springbootadventure.dto.book.BookSearchParametersDto;
import org.example.springbootadventure.dto.book.CreateBookRequestDto;
import org.springframework.data.domain.Page;


import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto createBook(CreateBookRequestDto requestDto);

    Page<BookDto> getAll(Pageable pageable);

    BookDto getBookById(Long id);

    public BookDto updateBook(Long id, CreateBookRequestDto requestDto);

    public void deleteById(Long id);

    public Page<BookDto> search(BookSearchParametersDto params,Pageable pageable);
}