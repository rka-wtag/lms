package com.lmsf.org.entity;

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
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Size(min = 1, max = 255)
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /*@ManyToMany(mappedBy = "genres")
    private List<Book> books = new ArrayList<>();*/
}
