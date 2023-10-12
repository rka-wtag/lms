package com.lmsf.org.controllers;

import com.lmsf.org.entity.Book;
import com.lmsf.org.dto.BookDto;
import com.lmsf.org.exception.AuthorNotFoundException;
import com.lmsf.org.exception.BookNotFoundException;
import com.lmsf.org.exception.GenreNotFoundException;
import com.lmsf.org.exception.UserNotFoundException;
import com.lmsf.org.service.BookGenreService;
import com.lmsf.org.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final BookGenreService bookGenreService;

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody @Valid BookDto bookDto) {
            Book savedBook = bookService.createBook(bookDto);
            bookGenreService.createBookGenre(bookDto.getGenreIds(), savedBook);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping
    public ResponseEntity<List<Book>> fetchBooks() {
        List<Book> books = bookService.fetchBooks();
        return ResponseEntity.ok(books);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@RequestBody @Valid BookDto bookDto, @PathVariable Long id) {
        Book updatedBook = bookService.updateBook(bookDto, id);
        bookGenreService.createBookGenre(bookDto.getGenreIds(), updatedBook);
        return ResponseEntity.ok(updatedBook);
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        Book updatedBook = bookService.getBook(id);
        return updatedBook;
    }

}
