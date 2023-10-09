package com.lmsf.org.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 1, max = 255)
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Size(min = 1, max = 255)
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Email
    @Size(min = 1, max = 255)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /*@JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private List<Book> book = new ArrayList<>();*/

}
