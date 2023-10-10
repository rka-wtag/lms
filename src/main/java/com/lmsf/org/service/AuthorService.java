package com.lmsf.org.service;

import com.lmsf.org.dto.AuthorDto;
import com.lmsf.org.entity.Author;
import com.lmsf.org.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public ResponseEntity<Author> createAuthor(AuthorDto authorDto){
        Author author = new Author();
        author.setFirstName(authorDto.getFirstName());
        author.setLastName(authorDto.getLastName());
        author.setEmail(authorDto.getEmail());
        Author savedAuthor = authorRepository.save(author);
        return ResponseEntity.ok(savedAuthor);
    }
    public Author getAuthor(Long id){
        Author author = authorRepository.findById(id).get();
        return author;
    }
    public Author updateAuthor(Author author){
        Author updatedAuthor = authorRepository.save(author);
        return updatedAuthor;
    }
    public void deleteAuthor(Long id){
        authorRepository.deleteById(id);
    }

}
