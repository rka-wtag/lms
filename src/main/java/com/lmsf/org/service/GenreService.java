package com.lmsf.org.service;

import com.lmsf.org.dto.GenreRequestDto;
import com.lmsf.org.dto.GenreResponseDto;
import com.lmsf.org.entity.Genre;
import com.lmsf.org.exception.ConstraintsViolationException;
import com.lmsf.org.exception.GenreDeleteException;
import com.lmsf.org.exception.GenreNotFoundException;
import com.lmsf.org.repository.BookRepository;
import com.lmsf.org.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Transactional
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

    @Transactional
    public void deleteGenre(Long id){
        if(!genreRepository.existsById(id)){
            throw new GenreNotFoundException("Genre not found with id : "+id);
        }

        if(!bookRepository.existsByGenresId(id))
            throw new GenreDeleteException("Cannot delete the genre with id '" + id + "' because it is associated with books.");
        genreRepository.deleteById(id);
    }

    @Transactional
    public GenreResponseDto updateGenre(Long id, GenreRequestDto genreRequestDto){
        if(genreRepository.existsByName(genreRequestDto.getName())){
            throw new ConstraintsViolationException("Genre already exists");
        }
        Genre newGenre = genreRepository.findById(id).orElseThrow(() -> new GenreNotFoundException("Genre not found with id : "+id));
        newGenre.setName(genreRequestDto.getName().toUpperCase());
        genreRepository.save(newGenre);
        return modelMapper.map(newGenre, GenreResponseDto.class);
    }

    @Transactional(readOnly = true)
    public GenreResponseDto getGenre(Long id){
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new GenreNotFoundException("Genre not found with id : "+id));
        return modelMapper.map(genre, GenreResponseDto.class);
    }

    @Transactional(readOnly = true)
    public Page<GenreResponseDto> fetchGenres(int pageNo, int pageSize, String field){
        Sort sort = Sort.by(Sort.Direction.ASC, field);
        Pageable pageable = PageRequest.of(pageNo, pageSize).withSort(sort);
        Page<Genre> pageGenres = genreRepository.findAll(pageable);
        List<Genre> genres = pageGenres.getContent();
        List<GenreResponseDto> genreResponseDto = genres.stream().map(genre -> modelMapper.map(genre, GenreResponseDto.class)).collect(Collectors.toList());
        return new PageImpl<>(genreResponseDto, pageable, genreResponseDto.size());
    }

}
