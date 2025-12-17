package com.library.lab4sring_boot.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = "books")
@EqualsAndHashCode(exclude = "books")
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @OneToMany(mappedBy = "author")
    @JsonIgnore   //prevents infinite JSON recursion
    private List<Book> books = new ArrayList<>();
}
