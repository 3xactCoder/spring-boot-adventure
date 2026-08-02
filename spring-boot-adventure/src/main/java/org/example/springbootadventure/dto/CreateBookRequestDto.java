package org.example.springbootadventure.dto;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NonNull;

@Data
public class CreateBookRequestDto {

    private String title;

    private String author;

    private String isbn;

    private BigDecimal price;

    private String description;

    private String coverImage;
}