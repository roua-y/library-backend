package com.library.lab4sring_boot.controller;

import com.library.lab4sring_boot.DTO.BookRequest;
import com.library.lab4sring_boot.DTO.BookResponse;
import com.library.lab4sring_boot.service.BookService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public BookResponse createBook(@Valid @RequestBody BookRequest request) {
        return bookService.saveBook(request);
    }

    @GetMapping("/category")
    public Set<BookResponse> getBooksByCategory(@RequestParam String category) {
        return bookService.findBooksByCategory(category);
    }

    @GetMapping
    public Set<BookResponse> getAllBooks() {
        return bookService.getAllBooks();
    }
}
