package com.library.lab4sring_boot.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;

@Data
public class BookRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "ISBN cannot be empty")
    private String isbn;

    @Min(value = 1, message = "Price must be positive")
    private double price;

    @Min(value = 1, message = "Quantity must be positive")
    private int quantity;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Author ID is required")
    private Long authorId;

    @NotNull(message = "Publisher ID is required")
    private Long publisherId;

    private Set<Long> tagIds;
}
