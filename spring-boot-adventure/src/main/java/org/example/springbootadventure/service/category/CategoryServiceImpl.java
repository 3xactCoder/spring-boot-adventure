package org.example.springbootadventure.service.category;

import lombok.RequiredArgsConstructor;
import org.example.springbootadventure.dto.category.CategoryDto;
import org.example.springbootadventure.exceptions.EntityNotFoundException;
import org.example.springbootadventure.mapper.BookMapper;
import org.example.springbootadventure.mapper.CategoryMapper;
import org.example.springbootadventure.model.Category;
import org.example.springbootadventure.repository.book.BookRepository;
import org.example.springbootadventure.repository.category.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category by id :" + id));
        return categoryMapper.toDto(category);

    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        Category entity = categoryMapper.toEntity(categoryDto);
        return categoryMapper.toDto(categoryRepository.save(entity));
    }

    @Override
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category by id :" + id));
        category.setName(categoryDto.getName());
        category.setDescription(category.getDescription());

        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);

    }
}
