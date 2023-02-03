package com.example.hotel.service;



import com.example.hotel.models.FileDB;
import com.example.hotel.models.UserEntity;
import com.example.hotel.repository.FileDBRepository;
import com.example.hotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FileStorageService {

  @Autowired
  private FileDBRepository fileDBRepository;
  @Autowired
  private UserRepository userRepository;

  public FileDB store(MultipartFile file, Long userId) throws IOException {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());

    FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes(), userId );
    return fileDBRepository.save(FileDB);
  }

  public FileDB getFile(String id) {
    return fileDBRepository.findById(id).get();
  }

  public List<FileDB> getFilesByUser(Long userId) {
    return fileDBRepository.findAllByUserId(userId);
  }
  public Stream<FileDB> getAllFiles() {
    return fileDBRepository.findAll().stream();
  }
}
