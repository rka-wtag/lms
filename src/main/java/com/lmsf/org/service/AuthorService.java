package com.lmsf.org.service;

import com.lmsf.org.dto.AuthorDto;
import com.lmsf.org.entity.Author;
import com.lmsf.org.entity.Book;
import com.lmsf.org.exception.AuthorNotFoundException;
import com.lmsf.org.exception.ConstraintsViolationException;
import com.lmsf.org.exception.GenreDeleteException;
import com.lmsf.org.repository.AuthorRepository;
import com.lmsf.org.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public Author createAuthor(AuthorDto authorDto){
        Author author = new Author();
        if(authorRepository.existsByEmail(authorDto.getEmail())){
            throw new ConstraintsViolationException("Email already exists");
        }
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
    public List<Book> getBooksByAuthor(Long id){
        return bookRepository.findByAuthorId(id);
    }
    public void deleteAuthor(Long id){
        if(!authorRepository.existsById(id)){
            throw new AuthorNotFoundException("Author not found with id : "+id);
        }
        List<Book> books = bookRepository.findByAuthorId(id);

        if(!books.isEmpty())
            throw new GenreDeleteException("Cannot delete the Author with id '" + id + "' because it is associated with " + books.size() + " books.");

        authorRepository.deleteById(id);
    }

}
