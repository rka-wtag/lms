package com.lmsf.org.controllers;

import com.lmsf.org.dto.AuthorRequestDto;
import com.lmsf.org.dto.AuthorResponseDto;
import com.lmsf.org.dto.BookResponseDto;
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
    public ResponseEntity<AuthorResponseDto> createAuthor(@RequestBody @Valid AuthorRequestDto authorRequestDto) {
        return new ResponseEntity<>(authorService.createAuthor(authorRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponseDto>> fetchAuthors() {
        return ResponseEntity.ok(authorService.fetchAuthors());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> updateAuthor(@RequestBody @Valid AuthorRequestDto authorRequestDto, @PathVariable Long id) {
        return ResponseEntity.ok(authorService.updateAuthor(authorRequestDto, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> getAuthor(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthor(id));
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<List<BookResponseDto>> getBooksByAuthor(@PathVariable Long id){
        List<BookResponseDto> books = authorService.getBooksByAuthor(id);
        return ResponseEntity.ok(books);
    }

}
