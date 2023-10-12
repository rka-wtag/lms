package com.lmsf.org.service;

import com.lmsf.org.dto.AuthorDto;
import com.lmsf.org.entity.Author;
import com.lmsf.org.entity.Book;
import com.lmsf.org.exception.AuthorNotFoundException;
import com.lmsf.org.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public Author createAuthor(AuthorDto authorDto){
        Author author = new Author();
        author.setFirstName(authorDto.getFirstName());
        author.setLastName(authorDto.getLastName());
        author.setEmail(authorDto.getEmail());
        Author savedAuthor = authorRepository.save(author);
        return savedAuthor;
    }

    public List<Author> fetchAuthors(){
        return authorRepository.findAll();
    }
    public Author getAuthor(Long id){
        Author author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException("Author not found with id : "+id));
        return author;
    }
    public Author updateAuthor(AuthorDto authorDto, Long id){
        Author author = new Author();
        author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException("Author not found with id : "+id));
        author.setFirstName(authorDto.getFirstName());
        author.setLastName(authorDto.getLastName());
        author.setEmail(authorDto.getEmail());
        Author updatedAuthor = authorRepository.save(author);
        return updatedAuthor;
    }
    public void deleteAuthor(Long id){
        authorRepository.deleteById(id);
    }

}
