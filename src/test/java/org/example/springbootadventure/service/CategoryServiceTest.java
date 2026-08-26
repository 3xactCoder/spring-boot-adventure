package org.example.springbootadventure.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.example.springbootadventure.dto.category.CategoryDto;
import org.example.springbootadventure.dto.category.CreateCategoryRequestDto;
import org.example.springbootadventure.exceptions.EntityNotFoundException;
import org.example.springbootadventure.mapper.CategoryMapper;
import org.example.springbootadventure.model.Category;
import org.example.springbootadventure.repository.category.CategoryRepository;
import org.example.springbootadventure.service.category.CategoryServiceImpl;
import org.example.springbootadventure.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Verify save() saves and returns valid CategoryDto")
    void save_ValidRequestDto_ReturnsCategoryDto() {
        CreateCategoryRequestDto requestDto = TestUtil.createCategoryRequestDto(
                "Fantasy", "Fantasy books");
        Category category = TestUtil.createCategory(null, "Fantasy", "Fantasy books");
        Category savedCategory = TestUtil.createCategory(1L, "Fantasy", "Fantasy books");
        CategoryDto expectedDto = TestUtil.createCategoryDto(1L, "Fantasy", "Fantasy books");

        when(categoryMapper.toEntity(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        when(categoryMapper.toDto(savedCategory)).thenReturn(expectedDto);

        CategoryDto actualDto = categoryService.save(requestDto);

        assertNotNull(actualDto);
        assertEquals(expectedDto, actualDto);
        verify(categoryRepository).save(category);
    }

    @Test
    @DisplayName("Verify getById() returns CategoryDto when category exists")
    void getById_ValidId_ReturnsCategoryDto() {
        Long categoryId = 1L;
        Category category = TestUtil.createCategory(categoryId, "Sci-Fi", "Science Fiction");
        CategoryDto expectedDto = TestUtil.createCategoryDto(
                categoryId, "Sci-Fi", "Science Fiction");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(expectedDto);

        CategoryDto actualDto = categoryService.getById(categoryId);

        assertNotNull(actualDto);
        assertEquals(expectedDto, actualDto);
        verify(categoryRepository).findById(categoryId);
    }

    @Test
    @DisplayName("Verify getById() throws EntityNotFoundException for non-existing id")
    void getById_InvalidId_ThrowsEntityNotFoundException() {
        Long categoryId = 999L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> categoryService.getById(categoryId));
        verify(categoryRepository).findById(categoryId);
    }

    @Test
    @DisplayName("Verify deleteById() deletes category when exists")
    void deleteById_ValidId_CallsRepository() {
        Long categoryId = 1L;

        when(categoryRepository.existsById(categoryId)).thenReturn(true);

        categoryService.deleteById(categoryId);

        verify(categoryRepository).deleteById(categoryId);
    }

    @Test
    @DisplayName("Verify deleteById() throws EntityNotFoundException when category not found")
    void deleteById_InvalidId_ThrowsEntityNotFoundException() {
        Long categoryId = 999L;
        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> categoryService.deleteById(categoryId));
        verify(categoryRepository).existsById(categoryId);
        verifyNoMoreInteractions(categoryRepository);
    }
}