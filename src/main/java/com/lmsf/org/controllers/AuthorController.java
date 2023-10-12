package com.lmsf.org.controllers;

import com.lmsf.org.dto.AuthorDto;
import com.lmsf.org.entity.Author;
import com.lmsf.org.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;
    @PostMapping
    public ResponseEntity<Author> createBook(@RequestBody @Valid AuthorDto authorDto) {
        Author author = authorService.createAuthor(authorDto);
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Author>> fetchAuthors() {
        List<Author> authors = authorService.fetchAuthors();
        return ResponseEntity.ok(authors);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
    @PutMapping("/{id}")
    public Author updateAuthor(@RequestBody @Valid AuthorDto authorDto, @PathVariable Long id) {
        Author updateAuthor = authorService.updateAuthor(authorDto, id);
        return updateAuthor;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable Long id) {
        Author author = authorService.getAuthor(id);
        return ResponseEntity.ok(author);
    }

}
