package org.example.springbootadventure.repository.book;

import lombok.RequiredArgsConstructor;
import org.example.springbootadventure.dto.book.BookSearchParametersDto;
import org.example.springbootadventure.model.Book;
import org.example.springbootadventure.repository.SpecificationBuilder;
import org.example.springbootadventure.repository.SpecificationProviderManager;
import org.example.springbootadventure.repository.spec.AuthorSpecificationProvider;
import org.example.springbootadventure.repository.spec.TitleSpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book,
        BookSearchParametersDto> {
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParameters) {
        Specification<Book> spec = Specification.where(null);

        if (searchParameters.authors() != null && searchParameters.authors().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider(AuthorSpecificationProvider.AUTHOR_KEY)
                    .getSpecification(searchParameters.authors()));
        }
        if (searchParameters.titles() != null && searchParameters.titles().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider(TitleSpecificationProvider.TITLE_KEY)
                    .getSpecification(searchParameters.titles()));
        }
        return spec;
    }
}
