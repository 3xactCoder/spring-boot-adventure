package org.example.springbootadventure.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.example.springbootadventure.dto.book.BookDto;
import org.example.springbootadventure.dto.book.CreateBookRequestDto;
import org.example.springbootadventure.exceptions.EntityNotFoundException;
import org.example.springbootadventure.mapper.BookMapper;
import org.example.springbootadventure.model.Book;
import org.example.springbootadventure.model.Category;
import org.example.springbootadventure.repository.book.BookRepository;
import org.example.springbootadventure.service.book.BookServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Verify createBook() saves and returns valid BookDto")
    void createBook_ValidRequestDto_ReturnsBookDto() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Sample Book");
        requestDto.setAuthor("Author");
        requestDto.setIsbn("978-0-13-468599-1");
        requestDto.setPrice(BigDecimal.valueOf(29.99));
        requestDto.setCategoryIds(List.of(1L));

        Category category = new Category();
        category.setId(1L);
        category.setName("Fiction");

        Book book = new Book();
        book.setTitle(requestDto.getTitle());
        book.setAuthor(requestDto.getAuthor());
        book.setIsbn(requestDto.getIsbn());
        book.setPrice(requestDto.getPrice());
        book.setCategories(Set.of(category));

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setTitle(book.getTitle());
        savedBook.setAuthor(book.getAuthor());
        savedBook.setIsbn(book.getIsbn());
        savedBook.setPrice(book.getPrice());
        savedBook.setCategories(book.getCategories());

        BookDto expectedDto = new BookDto();
        expectedDto.setId(1L);
        expectedDto.setTitle(savedBook.getTitle());
        expectedDto.setAuthor(savedBook.getAuthor());
        expectedDto.setIsbn(savedBook.getIsbn());
        expectedDto.setPrice(savedBook.getPrice());
        expectedDto.setCategoryIds(List.of(1L));

        when(bookMapper.toModel(requestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(savedBook);
        when(bookMapper.toDto(savedBook)).thenReturn(expectedDto);

        BookDto actualDto = bookService.createBook(requestDto);

        assertNotNull(actualDto);
        assertEquals(expectedDto, actualDto);
        verify(bookRepository).save(book);
    }

    @Test
    @DisplayName("Verify getAll() returns page of BookDto")
    void getAll_ValidPageable_ReturnsBookDtoPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Sample Book");

        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Sample Book");

        Page<Book> bookPage = new PageImpl<>(List.of(book), pageable, 1);

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        Page<BookDto> actualPage = bookService.getAll(pageable);

        assertEquals(1, actualPage.getTotalElements());
        assertEquals(List.of(bookDto), actualPage.getContent());
        verify(bookRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Verify getBookById() returns BookDto when book exists")
    void getBookById_ValidId_ReturnsBookDto() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Sample Book");

        BookDto expectedDto = new BookDto();
        expectedDto.setId(bookId);
        expectedDto.setTitle("Sample Book");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(expectedDto);

        BookDto actualDto = bookService.getBookById(bookId);

        assertNotNull(actualDto);
        assertEquals(expectedDto, actualDto);
        verify(bookRepository).findById(bookId);
    }

    @Test
    @DisplayName("Verify getBookById() throws EntityNotFoundException for non-existing id")
    void getBookById_InvalidId_ThrowsEntityNotFoundException() {
        Long bookId = 999L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.getBookById(bookId));
        verify(bookRepository).findById(bookId);
    }

    @Test
    @DisplayName("Verify updateBook() updates and returns BookDto")
    void updateBook_ValidIdAndRequest_ReturnsUpdatedBookDto() {
        Long bookId = 1L;
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Updated Title");
        requestDto.setCategoryIds(List.of(1L));

        Book existingBook = new Book();
        existingBook.setId(bookId);

        Book updatedBook = new Book();
        updatedBook.setId(bookId);
        updatedBook.setTitle(requestDto.getTitle());

        BookDto expectedDto = new BookDto();
        expectedDto.setId(bookId);
        expectedDto.setTitle(requestDto.getTitle());

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(updatedBook);
        when(bookMapper.toDto(updatedBook)).thenReturn(expectedDto);

        BookDto actualDto = bookService.updateBook(bookId, requestDto);

        assertNotNull(actualDto);
        assertEquals(expectedDto, actualDto);
        verify(bookRepository).save(existingBook);
    }

    @Test
    @DisplayName("Verify deleteById() delegates deletion to repository")
    void deleteById_ValidId_CallsRepository() {
        Long bookId = 1L;

        bookService.deleteById(bookId);

        verify(bookRepository).deleteById(bookId);
        verifyNoMoreInteractions(bookRepository);
    }
}