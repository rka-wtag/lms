package com.lmsf.org.service;

import com.lmsf.org.entity.Author;
import com.lmsf.org.entity.Book;
import com.lmsf.org.model.BookModel;
import com.lmsf.org.repository.AuthorRepository;
import com.lmsf.org.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public ResponseEntity<Book> createBook(@Validated BookModel bookModel){
        try {
            Book book = new Book();
            book.setTitle(bookModel.getTitle());
            book.setCopiesAvailable(bookModel.getCopies_available());
            book.setPublicationYear((bookModel.getPublication_year()));
            book.setAuthor(linkAuthor(bookModel.getAuthor_id()));

            final Book savedBook = bookRepository.save(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);

        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    public Author linkAuthor(Long author_id){
        return authorRepository.findById(author_id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Author with id %d not found", author_id)));
    }
    public Book getBook(Long id){
        Book book = bookRepository.findById(id).get();
        return book;
    }
    public Book updateBook(Book book){
        Book updatedBook = bookRepository.save(book);
        return updatedBook;
    }
    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }
}
