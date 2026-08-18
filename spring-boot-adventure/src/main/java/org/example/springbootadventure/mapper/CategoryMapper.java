package org.example.springbootadventure.mapper;

import org.example.springbootadventure.config.MapperConfig;
import org.example.springbootadventure.dto.category.CategoryDto;
import org.example.springbootadventure.model.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto categoryDTO);
}
