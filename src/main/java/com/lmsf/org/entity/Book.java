package com.lmsf.org.entity;



import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 1, max = 255)
    @Column(name = "title", nullable = false)
    private String title;
    @Size(min = 1, max = 255)
    @Column(name = "publication_year", nullable = false)
    private int publicationYear;
    @Column(name = "copies_available", nullable = false)
    private int copiesAvailable;

    @JsonBackReference
    @ManyToOne
    private Author author;

    /*@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres = new ArrayList<>();*/

}
