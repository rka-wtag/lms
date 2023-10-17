package com.lmsf.org.service;

import com.lmsf.org.entity.LibraryStaff;
import com.lmsf.org.exception.LibraryStaffNotFoundException;
import com.lmsf.org.repository.LibraryStaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryStaffService {

    private final LibraryStaffRepository libraryStaffRepository;

    public LibraryStaff createLibraryStaff(LibraryStaff libraryStaff){
        return libraryStaffRepository.save(libraryStaff);
    }
    public LibraryStaff getLibraryStaff(Long id){
        return libraryStaffRepository.findById(id).orElseThrow(() -> new LibraryStaffNotFoundException("No Staff with id : +id"+id));
    }
    public LibraryStaff updateLibraryStaff(LibraryStaff libraryStaff){
        return libraryStaffRepository.save(libraryStaff);
    }
    public void deleteLibraryStaff(Long id){
        libraryStaffRepository.deleteById(id);
    }


}
