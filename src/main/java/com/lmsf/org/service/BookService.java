package com.lmsf.org.service;

import com.lmsf.org.entity.Author;
import com.lmsf.org.entity.Book;
import com.lmsf.org.dto.BookDto;
import com.lmsf.org.exception.AuthorNotFoundException;
import com.lmsf.org.exception.UserNotFoundException;
import com.lmsf.org.repository.AuthorRepository;
import com.lmsf.org.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public Book createBook(BookDto bookDto) throws AuthorNotFoundException {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setCopiesAvailable(bookDto.getCopies_available());
        book.setPublicationYear((bookDto.getPublication_year()));
        book.setAuthor(linkAuthor(bookDto.getAuthor_id()));

        final Book savedBook = bookRepository.save(book);
        return savedBook;
    }

    public List<Book> fetchBooks(){
        return bookRepository.findAll();
    }
    public Author linkAuthor(Long author_id) throws AuthorNotFoundException {
        Author author = authorRepository.findById(author_id).orElse(null);
        if(author == null) throw new AuthorNotFoundException("Author not found with id : "+author_id);
        else return author;
    }
    public Book getBook(Long id) throws UserNotFoundException {
        Book book = bookRepository.findById(id).orElse(null);
        if(book == null) throw new UserNotFoundException("Book not found with id : "+id);
        else return book;
    }
    public Book updateBook(Book book){
        Book updatedBook = bookRepository.save(book);
        return updatedBook;
    }
    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }
}
