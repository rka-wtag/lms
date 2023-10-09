package com.lmsf.org.controllers;

import com.lmsf.org.entity.Genre;
import com.lmsf.org.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {

    public final GenreService genreService;

    @PostMapping("/")
    public Genre createGenre(@RequestBody Genre genre) {
        Genre savedGenre = genreService.createGenre(genre);
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
}
