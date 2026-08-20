package org.example.springbootadventure.repository.spec;

import java.util.Arrays;
import org.example.springbootadventure.model.Book;
import org.example.springbootadventure.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component

public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    public static final String TITLE_KEY = "title";

    @Override
    public String getKey() {
        return TITLE_KEY;
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> root
                .get("title").in(Arrays.stream(params).toArray());
    }
}


