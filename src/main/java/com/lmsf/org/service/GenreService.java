package com.lmsf.org.service;

import com.lmsf.org.dto.GenreRequestDto;
import com.lmsf.org.dto.GenreResponseDto;
import com.lmsf.org.entity.Book;
import com.lmsf.org.entity.Genre;
import com.lmsf.org.exception.ConstraintsViolationException;
import com.lmsf.org.exception.GenreDeleteException;
import com.lmsf.org.exception.GenreNotFoundException;
import com.lmsf.org.repository.BookRepository;
import com.lmsf.org.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public GenreResponseDto createGenre(GenreRequestDto genreRequestDto){
        Genre genre = new Genre();
        String genreName = genreRequestDto.getName().toUpperCase();
        if(genreRepository.existsByName(genreName)){
            throw new ConstraintsViolationException("Genre already exists");
        }
        genre.setName(genreName);
        genreRepository.save(genre);
        return modelMapper.map(genre, GenreResponseDto.class);
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

    public GenreResponseDto updateGenre(Long id, GenreRequestDto genreRequestDto){
        if(genreRepository.existsByName(genreRequestDto.getName())){
            throw new ConstraintsViolationException("Genre already exists");
        }
        Genre newGenre = genreRepository.findById(id).orElseThrow(() -> new GenreNotFoundException("Genre not found with id : "+id));
        newGenre.setName(genreRequestDto.getName().toUpperCase());
        genreRepository.save(newGenre);
        return modelMapper.map(newGenre, GenreResponseDto.class);
    }

    public GenreResponseDto getGenre(Long id){
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new GenreNotFoundException("Genre not found with id : "+id));
        return modelMapper.map(genre, GenreResponseDto.class);
    }

    public List<GenreResponseDto> fetchGenres(){
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

        return genres.stream().map(genre -> modelMapper.map(genre, GenreResponseDto.class)).collect(Collectors.toList());
    }

}
