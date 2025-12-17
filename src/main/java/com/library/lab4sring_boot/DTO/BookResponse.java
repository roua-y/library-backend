package com.library.lab4sring_boot.DTO;

import lombok.Data;

import java.util.Set;

@Data
public class BookResponse {

    private Long id;
    private String title;
    private String category;
    private String isbn;
    private double price;
    private int quantity;

    private String authorName;
    private String publisherName;
    private Set<String> tagNames;
}
