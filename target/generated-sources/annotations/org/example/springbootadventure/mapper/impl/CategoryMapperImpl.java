package org.example.springbootadventure.mapper.impl;

import javax.annotation.processing.Generated;
import org.example.springbootadventure.dto.category.CategoryDto;
import org.example.springbootadventure.dto.category.CreateCategoryRequestDto;
import org.example.springbootadventure.mapper.CategoryMapper;
import org.example.springbootadventure.model.Category;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-22T17:07:52+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDto toDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();

        if ( category.getId() != null ) {
            categoryDto.setId( category.getId() );
        }
        if ( category.getName() != null ) {
            categoryDto.setName( category.getName() );
        }
        if ( category.getDescription() != null ) {
            categoryDto.setDescription( category.getDescription() );
        }

        return categoryDto;
    }

    @Override
    public Category toEntity(CreateCategoryRequestDto categoryDto) {
        if ( categoryDto == null ) {
            return null;
        }

        Category category = new Category();

        if ( categoryDto.getName() != null ) {
            category.setName( categoryDto.getName() );
        }
        if ( categoryDto.getDescription() != null ) {
            category.setDescription( categoryDto.getDescription() );
        }

        return category;
    }

    @Override
    public void updateCategoryFromDto(CreateCategoryRequestDto categoryDto, Category category) {
        if ( categoryDto == null ) {
            return;
        }

        if ( categoryDto.getName() != null ) {
            category.setName( categoryDto.getName() );
        }
        else {
            category.setName( null );
        }
        if ( categoryDto.getDescription() != null ) {
            category.setDescription( categoryDto.getDescription() );
        }
        else {
            category.setDescription( null );
        }
    }
}
