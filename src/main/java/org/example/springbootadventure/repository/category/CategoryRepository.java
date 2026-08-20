package org.example.springbootadventure.repository.category;

import org.example.springbootadventure.dto.category.CategoryDto;
import org.example.springbootadventure.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    CategoryDto getCategoryById(Long id);
}
