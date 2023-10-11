package com.lmsf.org.service;

import com.lmsf.org.dto.GenreDto;
import com.lmsf.org.entity.Genre;
import com.lmsf.org.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public Genre createGenre(GenreDto genreDto){
        Genre genre = new Genre();
        genre.setName(genreDto.getName());
        Genre savedGenre = genreRepository.save(genre);
        return savedGenre;
    }

    public void deleteGenre(Long id){
        genreRepository.deleteById(id);
    }

    public Genre updateGenre(Long id, Genre genre){
        Genre newGenre = genreRepository.findById(id).get();
        newGenre.setName(genre.getName());
        genreRepository.save(newGenre);
        return newGenre;
    }

}
