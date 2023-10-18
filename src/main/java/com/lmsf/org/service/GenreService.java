package com.lmsf.org.service;

import com.lmsf.org.dto.GenreDto;
import com.lmsf.org.entity.Book;
import com.lmsf.org.entity.Genre;
import com.lmsf.org.exception.GenreDeleteException;
import com.lmsf.org.exception.GenreNotFoundException;
import com.lmsf.org.repository.BookRepository;
import com.lmsf.org.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    public Genre createGenre(GenreDto genreDto){
        Genre genre = new Genre();
        genre.setName(genreDto.getName().toUpperCase());
        return genreRepository.save(genre);
    }
    public void deleteGenre(Long id){
        List<Book> books = bookRepository.findByGenresId(id);
        if(!books.isEmpty())
            throw new GenreDeleteException("Cannot delete the genre with id '" + id + "' because it is associated with " + books.size() + " books.");
        genreRepository.deleteById(id);
    }
    public Genre updateGenre(Long id, Genre genre){
        Genre newGenre = genreRepository.findById(id).orElseThrow(() -> new GenreNotFoundException("Genre not found with id : "+id));
        newGenre.setName(genre.getName().toUpperCase());
        genreRepository.save(newGenre);
        return newGenre;
    }
    public Genre getGenre(Long id){
        return genreRepository.findById(id).orElseThrow(() -> new GenreNotFoundException("Genre not found with id : "+id));
    }
    public List<Genre> fetchGenres(){
        return genreRepository.findAll();
    }

}
