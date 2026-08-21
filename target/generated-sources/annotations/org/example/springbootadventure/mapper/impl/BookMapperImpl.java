package org.example.springbootadventure.mapper.impl;

import java.util.Set;
import javax.annotation.processing.Generated;
import org.example.springbootadventure.dto.book.BookDto;
import org.example.springbootadventure.dto.book.BookDtoWithoutCategoryIds;
import org.example.springbootadventure.dto.book.CreateBookRequestDto;
import org.example.springbootadventure.mapper.BookMapper;
import org.example.springbootadventure.model.Book;
import org.example.springbootadventure.model.Category;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-21T13:13:48+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public Book toModel(CreateBookRequestDto requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        Book book = new Book();

        if ( requestDto.getTitle() != null ) {
            book.setTitle( requestDto.getTitle() );
        }
        if ( requestDto.getAuthor() != null ) {
            book.setAuthor( requestDto.getAuthor() );
        }
        if ( requestDto.getIsbn() != null ) {
            book.setIsbn( requestDto.getIsbn() );
        }
        if ( requestDto.getPrice() != null ) {
            book.setPrice( requestDto.getPrice() );
        }
        if ( requestDto.getDescription() != null ) {
            book.setDescription( requestDto.getDescription() );
        }
        if ( requestDto.getCoverImage() != null ) {
            book.setCoverImage( requestDto.getCoverImage() );
        }

        return book;
    }

    @Override
    public BookDto toDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDto bookDto = new BookDto();

        if ( book.getId() != null ) {
            bookDto.setId( book.getId() );
        }
        if ( book.getTitle() != null ) {
            bookDto.setTitle( book.getTitle() );
        }
        if ( book.getAuthor() != null ) {
            bookDto.setAuthor( book.getAuthor() );
        }
        if ( book.getIsbn() != null ) {
            bookDto.setIsbn( book.getIsbn() );
        }
        if ( book.getPrice() != null ) {
            bookDto.setPrice( book.getPrice() );
        }
        if ( book.getDescription() != null ) {
            bookDto.setDescription( book.getDescription() );
        }
        if ( book.getCoverImage() != null ) {
            bookDto.setCoverImage( book.getCoverImage() );
        }

        setCategoryIds( bookDto, book );

        return bookDto;
    }

    @Override
    public Book toEntity(CreateBookRequestDto bookDto) {
        if ( bookDto == null ) {
            return null;
        }

        Book book = new Book();

        Set<Category> set = categoriesFromIds( bookDto.getCategoryIds() );
        if ( set != null ) {
            book.setCategories( set );
        }
        if ( bookDto.getTitle() != null ) {
            book.setTitle( bookDto.getTitle() );
        }
        if ( bookDto.getAuthor() != null ) {
            book.setAuthor( bookDto.getAuthor() );
        }
        if ( bookDto.getIsbn() != null ) {
            book.setIsbn( bookDto.getIsbn() );
        }
        if ( bookDto.getPrice() != null ) {
            book.setPrice( bookDto.getPrice() );
        }
        if ( bookDto.getDescription() != null ) {
            book.setDescription( bookDto.getDescription() );
        }
        if ( bookDto.getCoverImage() != null ) {
            book.setCoverImage( bookDto.getCoverImage() );
        }

        return book;
    }

    @Override
    public BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDtoWithoutCategoryIds bookDtoWithoutCategoryIds = new BookDtoWithoutCategoryIds();

        if ( book.getId() != null ) {
            bookDtoWithoutCategoryIds.setId( book.getId() );
        }
        if ( book.getTitle() != null ) {
            bookDtoWithoutCategoryIds.setTitle( book.getTitle() );
        }
        if ( book.getAuthor() != null ) {
            bookDtoWithoutCategoryIds.setAuthor( book.getAuthor() );
        }
        if ( book.getIsbn() != null ) {
            bookDtoWithoutCategoryIds.setIsbn( book.getIsbn() );
        }
        if ( book.getPrice() != null ) {
            bookDtoWithoutCategoryIds.setPrice( book.getPrice() );
        }
        if ( book.getDescription() != null ) {
            bookDtoWithoutCategoryIds.setDescription( book.getDescription() );
        }
        if ( book.getCoverImage() != null ) {
            bookDtoWithoutCategoryIds.setCoverImage( book.getCoverImage() );
        }

        return bookDtoWithoutCategoryIds;
    }

    @Override
    public void updateBookFromDto(CreateBookRequestDto requestDto, Book book) {
        if ( requestDto == null ) {
            return;
        }

        if ( book.getCategories() != null ) {
            Set<Category> set = categoriesFromIds( requestDto.getCategoryIds() );
            if ( set != null ) {
                book.getCategories().clear();
                book.getCategories().addAll( set );
            }
        }
        else {
            Set<Category> set = categoriesFromIds( requestDto.getCategoryIds() );
            if ( set != null ) {
                book.setCategories( set );
            }
        }
        if ( requestDto.getTitle() != null ) {
            book.setTitle( requestDto.getTitle() );
        }
        else {
            book.setTitle( null );
        }
        if ( requestDto.getAuthor() != null ) {
            book.setAuthor( requestDto.getAuthor() );
        }
        else {
            book.setAuthor( null );
        }
        if ( requestDto.getIsbn() != null ) {
            book.setIsbn( requestDto.getIsbn() );
        }
        else {
            book.setIsbn( null );
        }
        if ( requestDto.getPrice() != null ) {
            book.setPrice( requestDto.getPrice() );
        }
        else {
            book.setPrice( null );
        }
        if ( requestDto.getDescription() != null ) {
            book.setDescription( requestDto.getDescription() );
        }
        else {
            book.setDescription( null );
        }
        if ( requestDto.getCoverImage() != null ) {
            book.setCoverImage( requestDto.getCoverImage() );
        }
        else {
            book.setCoverImage( null );
        }
    }
}
