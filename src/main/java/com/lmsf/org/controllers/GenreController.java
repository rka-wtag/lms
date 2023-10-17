package com.lmsf.org.controllers;

import com.lmsf.org.dto.GenreDto;
import com.lmsf.org.entity.Book;
import com.lmsf.org.entity.Genre;
import com.lmsf.org.exception.BookNotFoundException;
import com.lmsf.org.service.BookService;
import com.lmsf.org.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Genre> createGenre(@RequestBody @Valid GenreDto genreDto) {
        return new ResponseEntity<>(genreService.createGenre(genreDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id){
        genreService.deleteGenre(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genre> updateGenre(@PathVariable Long id, @RequestBody Genre genre){
        return ResponseEntity.ok(genreService.updateGenre(id, genre));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenre(@PathVariable Long id){
        return ResponseEntity.ok(genreService.getGenre(id));
    }

    @GetMapping
    public ResponseEntity<List<Genre>> fetchGenres(){
        return ResponseEntity.ok(genreService.fetchGenres());
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<Set<Book>> getBooksByGenre(@PathVariable Long id){
        Set<Book> books = bookService.getBooksByGenre(id);
        if(books.isEmpty()){
            throw new BookNotFoundException("No books were found");
        }
        return ResponseEntity.ok(books);
    }

}
