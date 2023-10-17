package com.lmsf.org.controllers;

import com.lmsf.org.dto.AuthorDto;
import com.lmsf.org.entity.Author;
import com.lmsf.org.entity.Book;
import com.lmsf.org.exception.BookNotFoundException;
import com.lmsf.org.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

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
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@RequestBody @Valid AuthorDto authorDto, @PathVariable Long id) {
        return ResponseEntity.ok(authorService.updateAuthor(authorDto, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable Long id) {
        Author author = authorService.getAuthor(id);
        return ResponseEntity.ok(author);
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<Set<Book>> getBooksByAuthor(@PathVariable Long id){
        Set<Book> books = authorService.getBooksByAuthor(id);
        if(books.isEmpty()){
            throw new BookNotFoundException("No books were found");
        }
        return ResponseEntity.ok(books);
    }

}
