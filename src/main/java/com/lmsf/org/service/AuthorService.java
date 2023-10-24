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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto){
        if(authorRepository.existsByEmail(authorRequestDto.getEmail())){
            throw new ConstraintsViolationException("Email already exists");
        }
        Author author = modelMapper.map(authorRequestDto, Author.class);
        return modelMapper.map(authorRepository.save(author), AuthorResponseDto.class);

    }

    public List<AuthorResponseDto> fetchAuthors(){
        List<Author> authors = authorRepository.findAll();
        if(authors.isEmpty()){
            throw new AuthorNotFoundException("No author found");
        }
        return authors.stream().map(book -> modelMapper.map(book, AuthorResponseDto.class))
                .collect(Collectors.toList());
    }

    public AuthorResponseDto getAuthor(Long id){
        Author author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException("Author not found with id : "+id));
        return modelMapper.map(author, AuthorResponseDto.class);
    }

    public AuthorResponseDto updateAuthor(AuthorRequestDto authorRequestDto, Long id){
        Author author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException("Author not found with id : "+id));
        author.setFirstName(authorRequestDto.getFirstName());
        author.setLastName(authorRequestDto.getLastName());
        author.setEmail(authorRequestDto.getEmail());
        authorRepository.save(author);
        return modelMapper.map(author, AuthorResponseDto.class);
    }

    public List<BookResponseDto> getBooksByAuthor(Long id){
        if(!authorRepository.existsById(id)){
            throw new AuthorNotFoundException("Author not found with id : "+id);
        }
        List<Book> books = bookRepository.findByAuthorIdOrderById(id);

        if(books.isEmpty()){
            throw new BookNotFoundException("Noo books found");
        }
        return books
                .stream()
                .map(book -> modelMapper.map(book, BookResponseDto.class))
                .collect(Collectors.toList());
    }

    public void deleteAuthor(Long id){
        if(!authorRepository.existsById(id)){
            throw new AuthorNotFoundException("Author not found with id : "+id);
        }
        List<Book> books = bookRepository.findByAuthorIdOrderById(id);

        if(!books.isEmpty())
            throw new GenreDeleteException("Cannot delete the Author with id '" + id + "' because it is associated with " + books.size() + " books.");

        authorRepository.deleteById(id);
    }

}
