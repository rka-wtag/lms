package com.lmsf.org.controllers;

import com.lmsf.org.entity.Book;
import com.lmsf.org.dto.BookDto;
import com.lmsf.org.service.BookGenreService;
import com.lmsf.org.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final BookGenreService bookGenreService;
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody @Valid BookDto bookDto) {
            ResponseEntity<Book> response = bookService.createBook(bookDto);
            bookGenreService.createBookGenre(bookDto.getGenreIds(), response.getBody());
            return response;
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
    @PutMapping
    public Book updateBook(@RequestBody Book book) {
        Book updatedBook = bookService.updateBook(book);
        return updatedBook;
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        Book updatedBook = bookService.getBook(id);
        return updatedBook;
    }

}
