package com.lmsf.org.controllers;

import com.lmsf.org.dto.IssueRequestDto;
import com.lmsf.org.dto.IssueResponseDto;
import com.lmsf.org.entity.IssuedBook;
import com.lmsf.org.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/issue")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @PostMapping
    public ResponseEntity<IssueResponseDto> issuedBooks(
            @RequestBody @Valid IssueRequestDto issueRequestDto,
            @RequestHeader(value = "Authorization") String authHeader
    ){;
        return ResponseEntity.ok(issueService.issueBooks(issueRequestDto, authHeader));
    }
}
