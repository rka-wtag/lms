package com.lmsf.org.service;

import com.lmsf.org.dto.GenreDto;
import com.lmsf.org.entity.Book;
import com.lmsf.org.entity.Genre;
import com.lmsf.org.exception.ConstraintsViolationException;
import com.lmsf.org.exception.GenreDeleteException;
import com.lmsf.org.exception.GenreNotFoundException;
import com.lmsf.org.repository.BookRepository;
import com.lmsf.org.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    public Genre createGenre(GenreDto genreDto){
        Genre genre = new Genre();
        String genreName = genreDto.getName().toUpperCase();
        if(genreRepository.existsByName(genreName)){
            throw new ConstraintsViolationException("Genre already exists");
        }
        genre.setName(genreName);
        return genreRepository.save(genre);
    }
    public void deleteGenre(Long id){
        if(!genreRepository.existsById(id)){
            throw new GenreNotFoundException("Genre not found with id : "+id);
        }
        List<Book> books = bookRepository.findByGenresIdOrderById(id);
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
        List<Genre> genres = genreRepository.findAll();
        if(genres.isEmpty()){
            throw new GenreNotFoundException("Currently Genre list is empty");
        }
        genres.sort(new Comparator<Genre>() {
            @Override
            public int compare(Genre o1, Genre o2) {
                return (int) o1.getId() - (int) o2.getId();
            }
        });

        return genres;
    }

}
