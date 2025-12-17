package com.library.lab4sring_boot.repository;

import com.library.lab4sring_boot.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByCategory(String category);
}
