package com.lmsf.org.service;

import com.lmsf.org.dto.BookDto;
import com.lmsf.org.entity.Author;
import com.lmsf.org.entity.Book;
import com.lmsf.org.exception.AuthorNotFoundException;
import com.lmsf.org.exception.BookNotFoundException;
import com.lmsf.org.repository.AuthorRepository;
import com.lmsf.org.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public Book createBook(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setCopiesAvailable(bookDto.getCopiesAvailable());
        book.setPublicationYear((bookDto.getPublicationYear()));
        book.setAuthor(linkAuthor(bookDto.getAuthorId()));

        final Book savedBook = bookRepository.save(book);
        return savedBook;
    }

    public List<Book> fetchBooks(){
        return bookRepository.findAll();
    }
    public Author linkAuthor(Long author_id) {
        Author author = authorRepository.findById(author_id).orElseThrow(() -> new AuthorNotFoundException("Author not found with id : "+author_id));
        return author;
    }
    public Book getBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id : "+id));
        return book;
    }
    public Book updateBook(BookDto bookDto, Long id){
        Book book = new Book();
        book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id : "+id));
        book.setTitle(bookDto.getTitle());
        book.setCopiesAvailable(bookDto.getCopiesAvailable());
        book.setPublicationYear((bookDto.getPublicationYear()));
        book.setAuthor(linkAuthor(bookDto.getAuthorId()));
        Book updatedBook = bookRepository.save(book);
        return updatedBook;
    }
    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }
}
