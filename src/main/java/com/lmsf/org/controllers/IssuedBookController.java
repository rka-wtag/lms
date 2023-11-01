package com.lmsf.org.controllers;

import com.lmsf.org.dto.IssueRequestDto;
import com.lmsf.org.dto.IssueResponseDto;
import com.lmsf.org.dto.PageRequestDto;
import com.lmsf.org.service.IssuedBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/issued-books")
@RequiredArgsConstructor
public class IssuedBookController {

    private final IssuedBooksService issuedBooksService;

    @PostMapping
    public ResponseEntity<IssueResponseDto> issuedBooks(@RequestBody @Valid IssueRequestDto issueRequestDto){
        return ResponseEntity.ok(issuedBooksService.issueBooks(issueRequestDto));
    }

    @GetMapping
    public ResponseEntity<Page<IssueResponseDto>> fetchIssuedBooks(@Valid PageRequestDto pageRequestDto){
        return ResponseEntity.ok(issuedBooksService.fetchIssuedBooks(
                pageRequestDto.getPageNo(),
                pageRequestDto.getPageSize() == 0 ? 10 : pageRequestDto.getPageSize(),
                pageRequestDto.getSortingField() == null ? "id" : pageRequestDto.getSortingField()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> returnBooks(@PathVariable Long id){
        issuedBooksService.returnBooks(id);
        return ResponseEntity.ok().build();
    }


}
