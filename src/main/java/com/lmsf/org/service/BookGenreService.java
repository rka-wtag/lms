package com.lmsf.org.service;

import com.lmsf.org.entity.Book;
import com.lmsf.org.entity.BookGenre;
import com.lmsf.org.entity.Genre;
import com.lmsf.org.repository.BookGenreRepository;
import com.lmsf.org.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class BookGenreService {
    private final BookGenreRepository bookGenreRepository;
    private final GenreRepository genreRepository;
    public void createBookGenre(Set<Long> genreIds, Book book){

        for(Long gId : genreIds){
            Genre genre = genreRepository.findById(gId).get();
            BookGenre bookGenre = new BookGenre();

            bookGenre.setBook(book);
            bookGenre.setGenre(genre);

            bookGenreRepository.save(bookGenre);

        }




    }



}
