package com.lmsf.org.service;

import com.lmsf.org.config.MyCustomUserDetails;
import com.lmsf.org.dto.IssueRequestDto;
import com.lmsf.org.dto.IssueResponseDto;
import com.lmsf.org.entity.Book;
import com.lmsf.org.entity.IssuedBook;
import com.lmsf.org.entity.UserInfo;
import com.lmsf.org.exception.BookBorrowException;
import com.lmsf.org.exception.BookNotAvailableException;
import com.lmsf.org.exception.BookNotFoundException;
import com.lmsf.org.exception.BookReturnException;
import com.lmsf.org.repository.BookRepository;
import com.lmsf.org.repository.IssuedBookRepository;
import com.lmsf.org.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssuedBooksService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final IssuedBookRepository issuedBookRepository;
    private final ModelMapper modelMapper;


    @Transactional
    public IssueResponseDto issueBooks(IssueRequestDto issueRequestDto) {

        MyCustomUserDetails myCustomUserDetails = (MyCustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserInfo user = getUserDetails(myCustomUserDetails.getUsername());

        Book book = bookRepository.findById(issueRequestDto.getBookId())
                .orElseThrow(() -> new BookNotFoundException("Book not found with id : "+issueRequestDto.getBookId()));

        if(issuedBookRepository.existsByBookIdAndUserId(issueRequestDto.getBookId(), myCustomUserDetails.getId())){
            throw new BookBorrowException("You already have one copy of this book");
        }
        if(book.getCopiesAvailable() == 0){
            throw new BookNotAvailableException("Oops!! no copies available. Try issuing another book");
        }

        book.setCopiesAvailable(book.getCopiesAvailable() - 1);
        IssuedBook issuedBook = new IssuedBook();
        issuedBook.setUser(user);
        issuedBook.setBook(book);
        issuedBookRepository.save(issuedBook);

        IssueResponseDto issueResponseDto = modelMapper.map(issuedBook, IssueResponseDto.class);
        issueResponseDto.setIssuer(user.getName());
        return issueResponseDto;
    }

    @Transactional(readOnly = true)
    public Page<IssueResponseDto> fetchIssuedBooks(int pageNo, int pageSize, String field) {

        MyCustomUserDetails myCustomUserDetails = (MyCustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Sort sort = Sort.by(Sort.Direction.ASC, field);
        Long id = myCustomUserDetails.getId();
        Pageable pageable = PageRequest.of(pageNo, pageSize).withSort(sort);
        Page<IssuedBook> pageIssuedBooks =  issuedBookRepository.findByUserId(id, pageable);
        List<IssuedBook> issuedBooks = pageIssuedBooks.getContent();
        if(issuedBooks.isEmpty()){
            throw new BookNotFoundException("You dont have any issued books");
        }
        List<IssueResponseDto> issueResponseDtoList = issuedBooks.stream().map(book -> modelMapper.map(book, IssueResponseDto.class)).collect(Collectors.toList());
        List<IssueResponseDto> issueResponseDtoList1 = issueResponseDtoList.stream().peek(book -> book.setIssuer(myCustomUserDetails.getName())).collect(Collectors.toList());

        return new PageImpl<>(issueResponseDtoList1, pageable, issueResponseDtoList1.size());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public UserInfo getUserDetails(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }


    @Transactional
    public void returnBooks(Long id) {
        IssuedBook issuedBook = issuedBookRepository.findById(id).orElseThrow(() -> new BookReturnException("Enter a valid issued-book id"));
        Book book = bookRepository.findById(issuedBook.getBook().getId())
                .orElseThrow(() -> new BookNotFoundException("The book has already been removed from library"));

        book.setCopiesAvailable(book.getCopiesAvailable() + 1);

        issuedBookRepository.deleteById(id);

    }
}
