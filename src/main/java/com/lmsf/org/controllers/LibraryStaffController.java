package com.lmsf.org.controllers;

import com.lmsf.org.entity.LibraryStaff;
import com.lmsf.org.service.LibraryStaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/libraryStaffs")
@RequiredArgsConstructor
public class LibraryStaffController {
    private final LibraryStaffService libraryStaffService;

    @PostMapping("/")
    public ResponseEntity<LibraryStaff> createLibraryStaff(@RequestBody LibraryStaff libraryStaff) {
        return ResponseEntity.ok(libraryStaffService.createLibraryStaff(libraryStaff));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibraryStaff(@PathVariable Long id) {
        libraryStaffService.deleteLibraryStaff(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/")
    public ResponseEntity<LibraryStaff> updateLibraryStaff(@RequestBody LibraryStaff libraryStaff) {
        return ResponseEntity.ok(libraryStaffService.updateLibraryStaff(libraryStaff));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryStaff> getLibraryStaff(@PathVariable Long id) {
        return ResponseEntity.ok(libraryStaffService.getLibraryStaff(id));
    }



}
