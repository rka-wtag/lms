package com.lmsf.org.controllers;

import com.lmsf.org.dto.*;
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

        if(Objects.nonNull(title) && publicationYear > 0)
            return ResponseEntity.ok(bookService.getBooksByTitleAndPublicationYear(
                    title,
                    publicationYear,
                    bookSearchRequestDto.getPageNo(),
                    bookSearchRequestDto.getPageSize() > 0 ? bookSearchRequestDto.getPageSize() : 10
            ));
        else if(Objects.nonNull(title))
            return ResponseEntity.ok(bookService.getBooksByTitle(
                    title,
                    bookSearchRequestDto.getPageNo(),
                    bookSearchRequestDto.getPageSize() > 0 ? bookSearchRequestDto.getPageSize() : 10
            ));
        else if(publicationYear > 0)
            return ResponseEntity.ok(bookService.getBooksByPublicationYear(
                    publicationYear,
                    bookSearchRequestDto.getPageNo(),
                    bookSearchRequestDto.getPageSize() > 0 ? bookSearchRequestDto.getPageSize() : 10
            ));
        return ResponseEntity.ok(bookService.fetchBooks(
                bookSearchRequestDto.getPageNo(),
                bookSearchRequestDto.getPageSize() > 0 ? bookSearchRequestDto.getPageSize() : 10
        ));

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
    public ResponseEntity<Set<Genre>> getGenres(@PathVariable Long id, @Valid PageRequestDto pageRequestDto) {
        return ResponseEntity.ok(bookService.getGenres(id));
    }

}
