package com.lmsf.org.service;

import com.lmsf.org.dto.AuthorRequestDto;
import com.lmsf.org.dto.AuthorResponseDto;
import com.lmsf.org.dto.BookResponseDto;
import com.lmsf.org.entity.Author;
import com.lmsf.org.entity.Book;
import com.lmsf.org.exception.AuthorNotFoundException;
import com.lmsf.org.exception.BookNotFoundException;
import com.lmsf.org.exception.ConstraintsViolationException;
import com.lmsf.org.exception.GenreDeleteException;
import com.lmsf.org.repository.AuthorRepository;
import com.lmsf.org.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto){
        if(authorRepository.existsByEmail(authorRequestDto.getEmail()))
            throw new ConstraintsViolationException("Email already exists");

        Author author = modelMapper.map(authorRequestDto, Author.class);
        return modelMapper.map(authorRepository.save(author), AuthorResponseDto.class);
    }

    public Page<AuthorResponseDto> fetchAuthors(int pageNo, int pageSize, String field){

        Sort sort = Sort.by(Sort.Direction.ASC, field);
        Pageable pageable = PageRequest.of(pageNo, pageSize).withSort(sort);
        Page<Author> pageAuthors = authorRepository.findAll(pageable);
        List<Author> authors = pageAuthors.getContent();
        if(authors.isEmpty()){
            throw new AuthorNotFoundException("No author found");
        }
        List<AuthorResponseDto> authorResponseDtos = authors
                .stream()
                .map(author -> modelMapper.map(author, AuthorResponseDto.class))
                .collect(Collectors.toList());

        return new PageImpl<>(authorResponseDtos, pageable, authorResponseDtos.size());
    }

    @Transactional(readOnly = true)
    public AuthorResponseDto getAuthor(Long id){
        Author author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException("Author not found with id : "+id));
        return modelMapper.map(author, AuthorResponseDto.class);
    }

    @Transactional
    public AuthorResponseDto updateAuthor(AuthorRequestDto authorRequestDto, Long id){
        Author author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException("Author not found with id : "+id));
        author.setFirstName(authorRequestDto.getFirstName());
        author.setLastName(authorRequestDto.getLastName());
        author.setEmail(authorRequestDto.getEmail());
        authorRepository.save(author);
        return modelMapper.map(author, AuthorResponseDto.class);
    }

    @Transactional(readOnly = true)
    public Page<BookResponseDto> getBooksByAuthor(Long id, int pageNo, int pageSize, String field){
        if(!authorRepository.existsById(id)){
            throw new AuthorNotFoundException("Author not found with id : "+id);
        }
        Sort sort = Sort.by(Sort.Direction.ASC, field);
        Pageable pageable = PageRequest.of(pageNo, pageSize).withSort(sort);
        Page<Book> pageBooks = bookRepository.findByAuthorId(id, pageable);
        List<Book> books= pageBooks.getContent();
        List<BookResponseDto> bookResponseDtos = books
                .stream()
                .map(book -> modelMapper.map(book, BookResponseDto.class))
                .collect(Collectors.toList());
        return new PageImpl<>(bookResponseDtos, pageable, bookResponseDtos.size());
    }

    @Transactional
    public void deleteAuthor(Long id){
        if(!authorRepository.existsById(id)){
            throw new AuthorNotFoundException("Author not found with id : "+id);
        }

        if(!bookRepository.existsByAuthorId(id))
            throw new GenreDeleteException("Cannot delete the Author with id '" + id + "' because it is associated with books.");

        authorRepository.deleteById(id);
    }

}
