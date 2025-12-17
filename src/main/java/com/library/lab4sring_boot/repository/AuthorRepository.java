package com.library.lab4sring_boot.repository;
import com.library.lab4sring_boot.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AuthorRepository extends JpaRepository<Author, Long> {}
