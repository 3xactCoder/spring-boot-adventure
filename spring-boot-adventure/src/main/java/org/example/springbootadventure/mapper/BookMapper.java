package org.example.springbootadventure.mapper;

import org.example.springbootadventure.config.MapperConfig;
import org.example.springbootadventure.dto.book.BookDto;
import org.example.springbootadventure.dto.book.BookDtoWithoutCategoryIds;
import org.example.springbootadventure.dto.book.CreateBookRequestDto;
import org.example.springbootadventure.model.Book;
import org.example.springbootadventure.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapperConfig.class)
public interface BookMapper {

    Book toModel(CreateBookRequestDto requestDto);

    @Mapping(target = "categoryIds", ignore = true)
    BookDto toDto(Book book);

    @Mapping(target = "categories", source = "categoryIds", qualifiedByName = "categoriesFromIds")
    Book toEntity(CreateBookRequestDto bookDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @Mapping(target = "categories", source = "categoryIds", qualifiedByName = "categoriesFromIds")
    void updateBookFromDto(CreateBookRequestDto requestDto, @MappingTarget Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        if (book.getCategories() != null) {
            bookDto.setCategoryIds(book.getCategories().stream()
                    .map(Category::getId)
                    .toList());
        }
    }

    @Named("categoriesFromIds")
    default Set<Category> categoriesFromIds(List<Long> categoryIds) {
        if (categoryIds == null) {
            return null;
        }
        return categoryIds.stream()
                .map(Category::new)
                .collect(Collectors.toSet());
    }
}