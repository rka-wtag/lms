package com.lmsf.org.controllers;

import com.lmsf.org.entity.LibraryStaff;
import com.lmsf.org.entity.Member;
import com.lmsf.org.service.LibraryStaffService;
import com.lmsf.org.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/libraryStaffs")
@RequiredArgsConstructor
public class LibraryStaffController {
    private final LibraryStaffService libraryStaffService;
    @PostMapping("/")
    public LibraryStaff createLibraryStaff(@RequestBody LibraryStaff libraryStaff) {
        LibraryStaff savedLibraryStaff = libraryStaffService.createLibraryStaff(libraryStaff);
        return savedLibraryStaff;
    }
    @DeleteMapping("/{id}")
    public void deleteLibraryStaff(@PathVariable Long id) {
        libraryStaffService.deleteLibraryStaff(id);
    }
    @PutMapping("/")
    public LibraryStaff updateLibraryStaff(@RequestBody LibraryStaff libraryStaff) {
        LibraryStaff updateLibraryStaff = libraryStaffService.updateLibraryStaff(libraryStaff);
        return updateLibraryStaff;
    }
    @GetMapping("/{id}")
    public LibraryStaff getLibraryStaff(@PathVariable Long id) {
        LibraryStaff libraryStaff = libraryStaffService.getLibraryStaff(id);
        return libraryStaff;
    }



}
