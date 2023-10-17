package com.lmsf.org.controllers;

import com.lmsf.org.dto.BookDto;
import com.lmsf.org.entity.Book;
import com.lmsf.org.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Transactional
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody @Valid BookDto bookDto) {
            Book savedBook = bookService.createBook(bookDto);
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
        return ResponseEntity.ok(updatedBook);
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        Book updatedBook = bookService.getBook(id);
        return updatedBook;
    }

    @GetMapping("/byGenre/{id}")
    public ResponseEntity<Set<Book>> getBooksByGenre(@PathVariable Long id){
        Set<Book> books = bookService.getBooksByGenre(id);
        if(books.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/booksOfAuthor/{id}")
    public ResponseEntity<Set<Book>> getBooksByAuthor(@PathVariable Long id){
        Set<Book> books = bookService.getBooksByAuthor(id);
        if(books.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(books);
    }

}
