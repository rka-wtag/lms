package com.lmsf.org.service;

import com.lmsf.org.entity.LibraryStaff;
import com.lmsf.org.repository.LibraryStaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryStaffService {

    private final LibraryStaffRepository libraryStaffRepository;

    public LibraryStaff createLibraryStaff(LibraryStaff libraryStaff){
        LibraryStaff savedLibraryStaff = libraryStaffRepository.save(libraryStaff);
        return savedLibraryStaff;
    }

    public LibraryStaff getLibraryStaff(Long id){
        LibraryStaff libraryStaff = libraryStaffRepository.findById(id).get();
        return libraryStaff;
    }
    public LibraryStaff updateLibraryStaff(LibraryStaff libraryStaff){
        LibraryStaff updatedLibraryStaff = libraryStaffRepository.save(libraryStaff);
        return updatedLibraryStaff;
    }
    public void deleteLibraryStaff(Long id){
        libraryStaffRepository.deleteById(id);
    }


}
