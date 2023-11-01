package com.lmsf.org.controllers;

import com.lmsf.org.dto.BookResponseDto;
import com.lmsf.org.dto.GenreRequestDto;
import com.lmsf.org.dto.GenreResponseDto;
import com.lmsf.org.dto.PageRequestDto;
import com.lmsf.org.service.BookService;
import com.lmsf.org.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<GenreResponseDto> createGenre(@RequestBody @Valid GenreRequestDto genreRequestDto) {
        return new ResponseEntity<>(genreService.createGenre(genreRequestDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id){
        genreService.deleteGenre(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreResponseDto> updateGenre(@PathVariable Long id, @RequestBody GenreRequestDto genreRequestDto){
        return ResponseEntity.ok(genreService.updateGenre(id, genreRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreResponseDto> getGenre(@PathVariable Long id){
        return ResponseEntity.ok(genreService.getGenre(id));
    }

    @GetMapping
    public ResponseEntity<Page<GenreResponseDto>> fetchGenres(@Valid PageRequestDto pageRequestDto){
            return ResponseEntity.ok(genreService.fetchGenres(
                    pageRequestDto.getPageNo(),
                    pageRequestDto.getPageSize() > 0 ? pageRequestDto.getPageSize() : 10,
                    pageRequestDto.getSortingField() == null ? "name" : pageRequestDto.getSortingField()
            ));
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<Page<BookResponseDto>> getBooksByGenre(@PathVariable Long id, @Valid PageRequestDto pageRequestDto){
        return ResponseEntity.ok(bookService.getBooksByGenre(
                id,
                pageRequestDto.getPageNo(),
                pageRequestDto.getPageSize() > 0 ? pageRequestDto.getPageSize() : 10,
                pageRequestDto.getSortingField() == null ? "id" : pageRequestDto.getSortingField()
        ));
    }

}
