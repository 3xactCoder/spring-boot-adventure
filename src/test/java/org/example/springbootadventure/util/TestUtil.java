package org.example.springbootadventure.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.example.springbootadventure.dto.book.BookDto;
import org.example.springbootadventure.dto.book.CreateBookRequestDto;
import org.example.springbootadventure.dto.category.CategoryDto;
import org.example.springbootadventure.dto.category.CreateCategoryRequestDto;
import org.example.springbootadventure.model.Book;
import org.example.springbootadventure.model.Category;

public final class TestUtil {

    private TestUtil() {
    }

    public static Category createCategory(Long id, String name, String description) {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setDescription(description);
        return category;
    }

    public static CategoryDto createCategoryDto(Long id, String name, String description) {
        CategoryDto dto = new CategoryDto();
        dto.setId(id);
        dto.setName(name);
        dto.setDescription(description);
        return dto;
    }

    public static CreateCategoryRequestDto createCategoryRequestDto(
            String name, String description) {
        CreateCategoryRequestDto dto = new CreateCategoryRequestDto();
        dto.setName(name);
        dto.setDescription(description);
        return dto;
    }

    public static Book createBook(Long id, String title, String author, String isbn,
                                  BigDecimal price, Set<Category> categories) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setPrice(price);
        book.setCategories(categories);
        return book;
    }

    public static BookDto createBookDto(Long id, String title, String author, String isbn,
                                        BigDecimal price, List<Long> categoryIds) {
        BookDto dto = new BookDto();
        dto.setId(id);
        dto.setTitle(title);
        dto.setAuthor(author);
        dto.setIsbn(isbn);
        dto.setPrice(price);
        dto.setCategoryIds(categoryIds);
        return dto;
    }

    public static CreateBookRequestDto createBookRequestDto(String title, String author,
                                                            String isbn, BigDecimal price,
                                                            List<Long> categoryIds) {
        CreateBookRequestDto dto = new CreateBookRequestDto();
        dto.setTitle(title);
        dto.setAuthor(author);
        dto.setIsbn(isbn);
        dto.setPrice(price);
        dto.setCategoryIds(categoryIds);
        return dto;
    }
}
