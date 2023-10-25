package com.lmsf.org.service;

import com.lmsf.org.dto.BookResponseDto;
import com.lmsf.org.dto.IssueRequestDto;
import com.lmsf.org.dto.IssueResponseDto;
import com.lmsf.org.dto.UserResponseDto;
import com.lmsf.org.entity.Book;
import com.lmsf.org.entity.IssuedBook;
import com.lmsf.org.entity.UserInfo;
import com.lmsf.org.exception.BookNotAvailableException;
import com.lmsf.org.exception.BookNotFoundException;
import com.lmsf.org.repository.BookRepository;
import com.lmsf.org.repository.IssuedBookRepository;
import com.lmsf.org.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueService {
    private final  JwtService jwtService;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final IssuedBookRepository issuedBookRepository;
    private final ModelMapper modelMapper;

    public IssueResponseDto issueBooks(IssueRequestDto issueRequestDto, String authHeader) {
        String jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);

        UserInfo user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        Book book = bookRepository.findById(issueRequestDto.getBookId())
                .orElseThrow(() -> new BookNotFoundException("Book not found with id : "+issueRequestDto.getBookId()));

        if(book.getCopiesAvailable() == 0){
            throw new BookNotAvailableException("Oops!! no copies available");
        }

        book.setCopiesAvailable(book.getCopiesAvailable() - 1);
        IssuedBook issuedBook = new IssuedBook();
        issuedBook.setUser(user);
        issuedBook.setBook(book);
        issuedBookRepository.save(issuedBook);

        BookResponseDto bookResponseDto = modelMapper.map(book, BookResponseDto.class);
        UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);

        IssueResponseDto issueResponseDto = new IssueResponseDto();

        issueResponseDto.setId(issuedBook.getId());
        issueResponseDto.setIssuer(userResponseDto);
        issueResponseDto.setBook(bookResponseDto);

        return issueResponseDto;
    }
}
