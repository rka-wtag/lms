package com.lmsf.org.controllers;

import com.lmsf.org.dto.AuthorResponseDto;
import com.lmsf.org.dto.BookRequestDto;
import com.lmsf.org.dto.BookResponseDto;
import com.lmsf.org.dto.BookSearchRequestDto;
import com.lmsf.org.entity.Genre;
import com.lmsf.org.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponseDto> createBook(@RequestBody @Valid BookRequestDto bookRequestDto) {
            return new ResponseEntity<>(bookService.createBook(bookRequestDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> fetchBooks(@Valid BookSearchRequestDto bookSearchRequestDto) {

        String title = bookSearchRequestDto.getTitle();
        int publicationYear = bookSearchRequestDto.getPublicationYear();

        if(Objects.nonNull(title) && publicationYear > 0){
            return ResponseEntity.ok(bookService.getBooksByTitleAndPublicationYear(title, publicationYear));
        }
        else if(Objects.nonNull(title))
            return ResponseEntity.ok(bookService.getBooksByTitle(title));
        else if(publicationYear > 0)
            return ResponseEntity.ok(bookService.getBooksByPublicationYear(publicationYear));
        return ResponseEntity.ok(bookService.fetchBooks());

    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@RequestBody @Valid BookRequestDto bookRequestDto, @PathVariable Long id) {
        return ResponseEntity.ok(bookService.updateBook(bookRequestDto, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @GetMapping("/{id}/authors")
    public ResponseEntity<AuthorResponseDto> getAuthor(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getAuthor(id));
    }

    @GetMapping("/{id}/genres")
    public ResponseEntity<Set<Genre>> getGenres(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getGenres(id));
    }

}
