package com.lmsf.org.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 1, max = 255)
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Size(min = 1, max = 255)
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "number_of_books_borrowed")
    private int numberOfBooksBorrowed;

}
