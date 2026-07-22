package org.example.springbootadventure;

import java.math.BigDecimal;
import org.example.springbootadventure.model.Book;
import org.example.springbootadventure.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootAdventureApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdventureApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(BookService bookService) {
        return args -> {
            Book book = new Book();
            book.setTitle("Kobzar");
            book.setAuthor("Taras Shevchenko");
            book.setIsbn("978-617-7186-12-8");
            book.setPrice(new BigDecimal("250.00"));
            book.setDescription("Collection of poems");
            book.setCoverImage("kobzar.jpg");

            bookService.save(book);
            System.out.println("Saved book: " + book);

            System.out.println("All books from DB:");
            bookService.findAll().forEach(System.out::println);
        };
    }
}

