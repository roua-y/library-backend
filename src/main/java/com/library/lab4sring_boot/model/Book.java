package com.library.lab4sring_boot.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

@Getter
@Setter
@ToString(exclude = {"author", "publisher", "tags"})
@EqualsAndHashCode(exclude = {"author", "publisher", "tags"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;
    private String isbn;
    private double price;
    private int quantity;

    @ManyToOne
    private Author author;

    @ManyToOne
    private Publisher publisher;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_tags",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();



}
