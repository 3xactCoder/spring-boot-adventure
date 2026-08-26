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
import org.example.springbootadventure.util.TestUtil;
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
        CreateBookRequestDto requestDto = TestUtil.createBookRequestDto(
                "Sample Book", "Author", "978-0-13-468599-1",
                BigDecimal.valueOf(29.99), List.of(100L));
        Category category = TestUtil.createCategory(100L, "Fiction", "Fiction books");
        Book book = TestUtil.createBook(null, requestDto.getTitle(), requestDto.getAuthor(),
                requestDto.getIsbn(), requestDto.getPrice(), Set.of(category));
        Book savedBook = TestUtil.createBook(1L, book.getTitle(), book.getAuthor(),
                book.getIsbn(), book.getPrice(), book.getCategories());
        BookDto expectedDto = TestUtil.createBookDto(1L, savedBook.getTitle(),
                savedBook.getAuthor(), savedBook.getIsbn(), savedBook.getPrice(), List.of(100L));

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
        Book book = TestUtil.createBook(1L, "Sample Book", "Author",
                "978-0-13-468599-1", BigDecimal.valueOf(29.99), Set.of());
        BookDto bookDto = TestUtil.createBookDto(1L, "Sample Book", "Author",
                "978-0-13-468599-1", BigDecimal.valueOf(29.99), List.of());
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
        Book book = TestUtil.createBook(bookId, "Sample Book", "Author",
                "978-0-13-468599-1", BigDecimal.valueOf(29.99), Set.of());
        BookDto expectedDto = TestUtil.createBookDto(bookId, "Sample Book", "Author",
                "978-0-13-468599-1", BigDecimal.valueOf(29.99), List.of());

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
        CreateBookRequestDto requestDto = TestUtil.createBookRequestDto(
                "Updated Title", "Author", "978-0-13-468599-1",
                BigDecimal.valueOf(35.00), List.of(100L));
        Book existingBook = TestUtil.createBook(bookId, "Old Title", "Author",
                "978-0-13-468599-1", BigDecimal.valueOf(29.99), Set.of());
        Book updatedBook = TestUtil.createBook(bookId, requestDto.getTitle(),
                requestDto.getAuthor(), requestDto.getIsbn(), requestDto.getPrice(), Set.of());
        BookDto expectedDto = TestUtil.createBookDto(bookId, requestDto.getTitle(),
                requestDto.getAuthor(), requestDto.getIsbn(), requestDto.getPrice(), List.of(100L));

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