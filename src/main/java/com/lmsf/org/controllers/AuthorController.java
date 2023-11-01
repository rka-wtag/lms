package com.lmsf.org.controllers;

import com.lmsf.org.dto.AuthorRequestDto;
import com.lmsf.org.dto.AuthorResponseDto;
import com.lmsf.org.dto.BookResponseDto;
import com.lmsf.org.dto.PageRequestDto;
import com.lmsf.org.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorResponseDto> createAuthor(@RequestBody @Valid AuthorRequestDto authorRequestDto) {
        return new ResponseEntity<>(authorService.createAuthor(authorRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<AuthorResponseDto>> fetchAuthors(@Valid PageRequestDto pageRequestDto) {
            return ResponseEntity.ok(authorService.fetchAuthors(
                    pageRequestDto.getPageNo(),
                    pageRequestDto.getPageSize() > 0 ? pageRequestDto.getPageSize() : 10,
                    pageRequestDto.getSortingField() == null ? "id" : pageRequestDto.getSortingField()
            ));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> updateAuthor(@RequestBody @Valid AuthorRequestDto authorRequestDto, @PathVariable Long id) {
        return ResponseEntity.ok(authorService.updateAuthor(authorRequestDto, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> getAuthor(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthor(id));
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<Page<BookResponseDto>> getBooksByAuthor(@PathVariable Long id, @Valid PageRequestDto pageRequestDto){
            Page<BookResponseDto> books = authorService.getBooksByAuthor(
                    id,
                    pageRequestDto.getPageNo(),
                    pageRequestDto.getPageSize() > 0 ? pageRequestDto.getPageSize() : 10,
                    pageRequestDto.getSortingField() == null ? "id" : pageRequestDto.getSortingField()
            );
            return ResponseEntity.ok(books);
    }

}
