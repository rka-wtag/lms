package com.lmsf.org.service;

import com.lmsf.org.dto.AuthorResponseDto;
import com.lmsf.org.dto.BookRequestDto;
import com.lmsf.org.dto.BookResponseDto;
import com.lmsf.org.entity.Author;
import com.lmsf.org.entity.Book;
import com.lmsf.org.entity.Genre;
import com.lmsf.org.exception.AuthorNotFoundException;
import com.lmsf.org.exception.BookDeleteException;
import com.lmsf.org.exception.BookNotFoundException;
import com.lmsf.org.exception.GenreNotFoundException;
import com.lmsf.org.repository.AuthorRepository;
import com.lmsf.org.repository.BookRepository;
import com.lmsf.org.repository.GenreRepository;
import com.lmsf.org.repository.IssuedBookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final IssuedBookRepository issuedBookRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public BookResponseDto createBook(BookRequestDto bookRequestDto) {
        Book book = modelMapper.map(bookRequestDto, Book.class);

        book.setId(null);

        Author author = authorRepository.findById(bookRequestDto.getAuthorId())
                .orElseThrow(() -> new AuthorNotFoundException("Author not found with id : "+ bookRequestDto.getAuthorId()));
        book.setAuthor(author);
        Set<Genre> genres = new HashSet<>();

        for(Long genreId : bookRequestDto.getGenreIds()){
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new GenreNotFoundException("genre not found with id : " +genreId));
            genres.add(genre);
        }

        book.setGenres(genres);
        bookRepository.save(book);
        return modelMapper.map(book, BookResponseDto.class);
    }

    @Transactional(readOnly = true)
    public Page<BookResponseDto> fetchBooks(int pageNo, int pageSize, String field){
        Sort sort = Sort.by(Sort.Direction.ASC, field);
        Pageable pageable = PageRequest.of(pageNo, pageSize).withSort(sort);
        Page<Book> pageBooks = bookRepository.findAll(pageable);
        List<Book> books = pageBooks.getContent();

        if(books.isEmpty())
            throw new BookNotFoundException("No Books were found");

        List<BookResponseDto> bookResponseDtos = books
                .stream()
                .map(book -> modelMapper.map(book, BookResponseDto.class))
                .collect(Collectors.toList());
        return new PageImpl<>(bookResponseDtos, pageable, bookResponseDtos.size());

    }

    @Transactional(readOnly = true)
    public Page<BookResponseDto> getBooksByGenre(Long id, int pageNo, int pageSize, String field){

        Sort sort = Sort.by(Sort.Direction.ASC, field);
        Pageable pageable = PageRequest.of(pageNo, pageSize).withSort(sort);
        Page<Book> pageBooks = bookRepository.findByGenresId(id,pageable);
        List<Book> books = pageBooks.getContent();

        if(books.isEmpty()){
            throw new BookNotFoundException("No books were found");
        }
        List<BookResponseDto> bookResponseDtos = books
                .stream()
                .map(book -> modelMapper.map(book, BookResponseDto.class))
                .collect(Collectors.toList());
        return new PageImpl<>(bookResponseDtos, pageable, bookResponseDtos.size());
    }

    @Transactional(readOnly = true)
    public Page<BookResponseDto> getBooksByTitle(String title, int pageNo, int pageSize, String field){
        Sort sort = Sort.by(Sort.Direction.ASC, field);
        Pageable pageable = PageRequest.of(pageNo, pageSize).withSort(sort);
        Page<Book> pageBooks = bookRepository.findByTitle(title, pageable);
        List<Book> books = pageBooks.getContent();

        if(books.isEmpty())
            throw new BookNotFoundException("No Books were found with name : "+title);

        List<BookResponseDto> bookResponseDtos = books
                .stream()
                .map(book -> modelMapper.map(book, BookResponseDto.class))
                .collect(Collectors.toList());
        return new PageImpl<>(bookResponseDtos, pageable, bookResponseDtos.size());
    }

    @Transactional(readOnly = true)
    public Page<BookResponseDto> getBooksByPublicationYear(int publicationYear, int pageNo, int pageSize, String field){
        Sort sort = Sort.by(Sort.Direction.ASC, field);
        Pageable pageable = PageRequest.of(pageNo, pageSize).withSort(sort);
        Page<Book> pageBooks = bookRepository.findByPublicationYear(publicationYear, pageable);
        List<Book> books = pageBooks.getContent();

        if(books.isEmpty())
            throw new BookNotFoundException("No Books were found that's published on : "+publicationYear);

        List<BookResponseDto> bookResponseDtos = books
                .stream()
                .map(book -> modelMapper.map(book, BookResponseDto.class))
                .collect(Collectors.toList());
        return new PageImpl<>(bookResponseDtos, pageable, bookResponseDtos.size());

    }

    @Transactional(readOnly = true)
    public BookResponseDto getBook(Long id) {
        Book book =  bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id : "+id));
        return modelMapper.map(book, BookResponseDto.class);
    }

    @Transactional(readOnly = true)
    public AuthorResponseDto getAuthor(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id : "+id));
        Author author = book.getAuthor();
        return modelMapper.map(author, AuthorResponseDto.class);
    }

    @Transactional
    public BookResponseDto updateBook(BookRequestDto bookRequestDto, Long id){
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id : "+id));
        book.setTitle(bookRequestDto.getTitle());
        book.setCopiesAvailable(bookRequestDto.getCopiesAvailable());
        book.setPublicationYear((bookRequestDto.getPublicationYear()));
        Author author = authorRepository.findById(bookRequestDto.getAuthorId())
                .orElseThrow(() -> new AuthorNotFoundException("Author not found with id : "+ bookRequestDto.getAuthorId()));
        book.setAuthor(author);

        Set<Genre> genres = new HashSet<>();

        for(Long genreId : bookRequestDto.getGenreIds()){
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new GenreNotFoundException("genre not found with id : id" +genreId));
            genres.add(genre);
        }
        book.setGenres(genres);
        bookRepository.save(book);
        return modelMapper.map(book, BookResponseDto.class);
    }

    @Transactional
    public void deleteBook(Long id){
        if(!bookRepository.existsById(id))
            throw new BookNotFoundException("Book not found with id : "+id);

        if(issuedBookRepository.existsByBookId(id)){
            throw new BookDeleteException("The book has already been issued by an user");
        }
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<Genre> getGenres(Long id, int pageNo, int pageSize, String field) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id : "+id));
        Set<Genre> genres = book.getGenres();
        List<Genre> genreList = new ArrayList<>(genres);
        Sort sort = Sort.by(Sort.Direction.ASC, field);
        Pageable pageable = PageRequest.of(pageNo, pageSize).withSort(sort);
        return new PageImpl<>(genreList, pageable, genreList.size());

    }

    @Transactional(readOnly = true)
    public Page<BookResponseDto> getBooksByTitleAndPublicationYear(String title, int publicationYear, int pageNo, int pageSize, String field) {
        if(!bookRepository.existsByTitleAndPublicationYear(title, publicationYear))
            throw new BookNotFoundException("No books were found");

        Sort sort = Sort.by(Sort.Direction.ASC, field);
        Pageable pageable = PageRequest.of(pageNo, pageSize).withSort(sort);
        Page<Book> pageBooks = bookRepository.findByTitleAndPublicationYear(title,publicationYear, pageable);
        List<Book> books = pageBooks.getContent();
        List<BookResponseDto> bookResponseDtos = books
                .stream()
                .map(book -> modelMapper.map(book, BookResponseDto.class))
                .collect(Collectors.toList());
        return new PageImpl<>(bookResponseDtos, pageable, bookResponseDtos.size());
    }
}
