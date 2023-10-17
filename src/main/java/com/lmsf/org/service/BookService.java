package com.lmsf.org.service;

import com.lmsf.org.dto.BookDto;
import com.lmsf.org.entity.Author;
import com.lmsf.org.entity.Book;
import com.lmsf.org.entity.Genre;
import com.lmsf.org.exception.AuthorNotFoundException;
import com.lmsf.org.exception.BookNotFoundException;
import com.lmsf.org.exception.GenreNotFoundException;
import com.lmsf.org.repository.AuthorRepository;
import com.lmsf.org.repository.BookRepository;
import com.lmsf.org.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public Book createBook(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setCopiesAvailable(bookDto.getCopiesAvailable());
        book.setPublicationYear((bookDto.getPublicationYear()));
        book.setAuthor(linkAuthor(bookDto.getAuthorId()));
        Set<Genre> genres = new HashSet<>();

        for(Long genreId : bookDto.getGenreIds()){
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new GenreNotFoundException("genre not found with id : id" +genreId));
            genres.add(genre);
        }

        book.setGenres(genres);
        return bookRepository.save(book);
    }
    public List<Book> fetchBooks(){
        return bookRepository.findAll();
    }
    public Set<Book> getBooksByGenre(Long id){
        return bookRepository.findByGenresId(id);
    }
    public Author linkAuthor(Long author_id) {
        return authorRepository.findById(author_id)
                .orElseThrow(() -> new AuthorNotFoundException("Author not found with id : "+author_id));
    }
    public Book getBook(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id : "+id));
    }
    public Book updateBook(BookDto bookDto, Long id){
        Book book = new Book();
        book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id : "+id));
        book.setTitle(bookDto.getTitle());
        book.setCopiesAvailable(bookDto.getCopiesAvailable());
        book.setPublicationYear((bookDto.getPublicationYear()));
        book.setAuthor(linkAuthor(bookDto.getAuthorId()));

        Set<Genre> genres = new HashSet<>();

        for(Long genreId : bookDto.getGenreIds()){
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new GenreNotFoundException("genre not found with id : id" +genreId));
            genres.add(genre);
        }
        book.setGenres(genres);
        return bookRepository.save(book);
    }
    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }
}
