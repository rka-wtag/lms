package com.lmsf.org.service;

import com.lmsf.org.entity.Author;
import com.lmsf.org.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public Author createAuthor(Author author){
        Author savedAuthor = authorRepository.save(author);
        return savedAuthor;
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
