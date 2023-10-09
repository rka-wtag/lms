package com.lmsf.org.controllers;

import com.lmsf.org.entity.Author;
import com.lmsf.org.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;
    @PostMapping("/")
    public Author createBook(@RequestBody Author author) {
        Author savedAuthor = authorService.createAuthor(author);
        return savedAuthor;
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
    @PutMapping("/")
    public Author updateAuthor(@RequestBody Author author) {
        Author updateAuthor = authorService.updateAuthor(author);
        return updateAuthor;
    }

    @GetMapping("/{id}")
    public Author getAuthor(@PathVariable Long id) {
        Author author = authorService.getAuthor(id);
        return author;
    }

}
