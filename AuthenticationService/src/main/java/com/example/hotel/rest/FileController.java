package com.example.hotel.rest;


import com.example.hotel.message.ResponseFile;
import com.example.hotel.message.ResponseMessage;
import com.example.hotel.models.FileDB;
import com.example.hotel.repository.FileDBRepository;
import com.example.hotel.repository.UserRepository;
import com.example.hotel.security.CurrentUser;
import com.example.hotel.security.UserPrincipal;
import com.example.hotel.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin("http://localhost:8081")
public class FileController {

  @Autowired
  private FileStorageService storageService;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private FileDBRepository fileDBRepository;

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@CurrentUser UserPrincipal userPrincipal , @RequestParam("file") MultipartFile file) {
    String message = "";
    try {
      storageService.store(file, userPrincipal.getId());

      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }

  @GetMapping("/files")
  public ResponseEntity<List<ResponseFile>> getListFiles(@CurrentUser UserPrincipal userPrincipal) {
    List<ResponseFile> files = storageService.getFilesByUser(userPrincipal.getId()).stream().map(dbFile -> {
      String fileDownloadUri = ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("/files/")
          .path(dbFile.getId())
          .toUriString();

      return new ResponseFile(
          dbFile.getName(),
          fileDownloadUri,
          dbFile.getType(),
          dbFile.getData().length);
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(files);
  }

  @GetMapping("/files/{id}")
  public ResponseEntity<byte[]> getFile(@PathVariable String id) {
    FileDB fileDB = storageService.getFile(id);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
        .body(fileDB.getData());
  }

  @DeleteMapping("/files/{id}")
  public ResponseEntity<?> delete(@PathVariable String id) {
    fileDBRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }


}
