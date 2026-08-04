package org.example.springbootadventure.repository.book;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.springbootadventure.model.Book;
import org.example.springbootadventure.repository.SpecificationProvider;
import org.example.springbootadventure.repository.SpecificationProviderManager;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(provider -> provider.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't find correct specification provider for key " + key));
    }
}