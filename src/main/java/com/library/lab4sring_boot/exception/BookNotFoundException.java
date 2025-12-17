package com.library.lab4sring_boot.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String message) {
        super(message);
    }

    public static BookNotFoundException forCategory(String category) {
        return new BookNotFoundException("Book with category '" + category + "' does not exist");
    }

    public static BookNotFoundException forId(Long id) {
        return new BookNotFoundException("Book with ID " + id + " does not exist");
    }
}
