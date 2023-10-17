package com.lmsf.org.controllers;

import com.lmsf.org.dto.GenreDto;
import com.lmsf.org.entity.Genre;
import com.lmsf.org.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {

    public final GenreService genreService;

    @PostMapping
    public Genre createGenre(@RequestBody @Valid GenreDto genreDto) {
        Genre savedGenre = genreService.createGenre(genreDto);
        return savedGenre;
    }

    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable Long id){
        genreService.deleteGenre(id);
    }

    @PutMapping("/{id}")
    public Genre updateGenre(@PathVariable Long id, @RequestBody Genre genre){
        Genre updateGenre = genreService.updateGenre(id, genre);
        return updateGenre;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenre(@PathVariable Long id){
        return ResponseEntity.ok(genreService.getGenre(id));
    }

    @GetMapping
    public ResponseEntity<List<Genre>> fetchGenres(){
        return ResponseEntity.ok(genreService.fetchGenres());
    }


}
