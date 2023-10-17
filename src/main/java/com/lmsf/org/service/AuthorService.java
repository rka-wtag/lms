package com.lmsf.org.service;

import com.lmsf.org.dto.AuthorDto;
import com.lmsf.org.entity.Author;
import com.lmsf.org.entity.Book;
import com.lmsf.org.exception.AuthorNotFoundException;
import com.lmsf.org.repository.AuthorRepository;
import com.lmsf.org.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public Author createAuthor(AuthorDto authorDto){
        Author author = new Author();
        author.setFirstName(authorDto.getFirstName());
        author.setLastName(authorDto.getLastName());
        author.setEmail(authorDto.getEmail());
        return authorRepository.save(author);
    }

    public List<Author> fetchAuthors(){
        return authorRepository.findAll();
    }
    public Author getAuthor(Long id){
        return authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException("Author not found with id : "+id));
    }
    public Author updateAuthor(AuthorDto authorDto, Long id){
        Author author = new Author();
        author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException("Author not found with id : "+id));
        author.setFirstName(authorDto.getFirstName());
        author.setLastName(authorDto.getLastName());
        author.setEmail(authorDto.getEmail());
        return authorRepository.save(author);
    }
    public Set<Book> getBooksByAuthor(Long id){
        return bookRepository.findByAuthorId(id);
    }
    public void deleteAuthor(Long id){
        authorRepository.deleteById(id);
    }

}
