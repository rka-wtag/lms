package com.lmsf.org.controllers;

import com.lmsf.org.dto.BookDto;
import com.lmsf.org.dto.BookSearchRequestDto;
import com.lmsf.org.entity.Author;
import com.lmsf.org.entity.Book;
import com.lmsf.org.entity.Genre;
import com.lmsf.org.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody @Valid BookDto bookDto) {
            return new ResponseEntity<>(bookService.createBook(bookDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Book>> fetchBooks(@Valid BookSearchRequestDto bookSearchRequestDto) {

        String title = bookSearchRequestDto.getTitle();
        int publicationYear = bookSearchRequestDto.getPublicationYear();

        if(Objects.nonNull(title))
            return ResponseEntity.ok(bookService.getBooksByTitle(title));
        else if(publicationYear > 0)
            return ResponseEntity.ok(bookService.getBooksByPublicationYear(publicationYear));
        return ResponseEntity.ok(bookService.fetchBooks());

    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@RequestBody @Valid BookDto bookDto, @PathVariable Long id) {
        return ResponseEntity.ok(bookService.updateBook(bookDto, id));
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @GetMapping("/{id}/authors")
    public Author getAuthor(@PathVariable Long id) {
        return bookService.getAuthor(id);
    }

    @GetMapping("/{id}/genres")
    public Set<Genre> getGenres(@PathVariable Long id) {
        return bookService.getGenres(id);
    }

}
