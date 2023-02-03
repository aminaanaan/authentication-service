package com.example.hotel.repository;


import com.example.hotel.models.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {
List<FileDB> findAllByUserId(Long userId);

void deleteById(String id);
}
