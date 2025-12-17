package com.library.lab4sring_boot.service;

import com.library.lab4sring_boot.DTO.BookRequest;
import com.library.lab4sring_boot.DTO.BookResponse;
import com.library.lab4sring_boot.exception.BookNotFoundException;
import com.library.lab4sring_boot.model.*;
import com.library.lab4sring_boot.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final TagRepository tagRepository;

    public BookService(BookRepository bookRepository,
            AuthorRepository authorRepository,
            PublisherRepository publisherRepository,
            TagRepository tagRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.tagRepository = tagRepository;
    }

    @Transactional
    public BookResponse saveBook(BookRequest request) {

        // 1️⃣ Load Author
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author with ID " + request.getAuthorId() + " not found"));

        // 2️⃣ Load Publisher
        Publisher publisher = publisherRepository.findById(request.getPublisherId())
                .orElseThrow(
                        () -> new RuntimeException("Publisher with ID " + request.getPublisherId() + " not found"));

        // 3️⃣ Load Tags
        Set<Tag> tags = new HashSet<>();
        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            tags.addAll(tagRepository.findAllById(request.getTagIds()));
        }

        // 4️⃣ Create Book entity
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setCategory(request.getCategory());
        book.setIsbn(request.getIsbn());
        book.setPrice(request.getPrice());
        book.setQuantity(request.getQuantity());
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setTags(tags);

        // 5️⃣ Save to DB
        Book savedBook = bookRepository.save(book);

        // 6️⃣ Map to BookResponse
        return mapToBookResponse(savedBook);
    }

    @Transactional(readOnly = true)
    public Set<BookResponse> findBooksByCategory(String category) {

        Set<Book> books = new HashSet<>(bookRepository.findByCategory(category));

        if (books.isEmpty()) {
            throw new BookNotFoundException(category);
        }

        return books.stream()
                .map(this::mapToBookResponse)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Set<BookResponse> getAllBooks() {
        Set<Book> books = new HashSet<>(bookRepository.findAll());
        return books.stream()
                .map(this::mapToBookResponse)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public java.util.List<BookResponse> getRecentBooks() {
        // Fetch all, sort by ID desc (newest first), limit to 3
        return bookRepository.findAll().stream()
                .sorted((b1, b2) -> b2.getId().compareTo(b1.getId()))
                .limit(3)
                .map(this::mapToBookResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public long countBooks() {
        return bookRepository.count();
    }

    // Helper method to map Book entity to DTO
    private BookResponse mapToBookResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setCategory(book.getCategory());
        response.setIsbn(book.getIsbn());
        response.setPrice(book.getPrice());
        response.setQuantity(book.getQuantity());
        response.setAuthorName(book.getAuthor().getName());
        response.setPublisherName(book.getPublisher().getName());
        response.setTagNames(book.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.toSet()));
        return response;
    }
}
