package com.kshrd.wearekhmer.files.service;


import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {

    String uploadFile(MultipartFile multipartFile) throws IOException;
    ResponseEntity<?> getFile(String fileName) throws IOException;

    ResponseEntity<?> uploadMultiFile(MultipartFile[] files) throws IOException;
}
