package com.lmsf.org.controllers;

import com.lmsf.org.entity.Book;
import com.lmsf.org.dto.BookDto;
import com.lmsf.org.exception.AuthorNotFoundException;
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
    public ResponseEntity<Book> createBook(@RequestBody @Valid BookDto bookDto) throws AuthorNotFoundException, GenreNotFoundException {
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
    @PutMapping
    public Book updateBook(@RequestBody Book book) {
        Book updatedBook = bookService.updateBook(book);
        return updatedBook;
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) throws UserNotFoundException {
        Book updatedBook = bookService.getBook(id);
        return updatedBook;
    }

}
