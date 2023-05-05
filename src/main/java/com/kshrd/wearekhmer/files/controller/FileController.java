package com.kshrd.wearekhmer.files.controller;


import com.kshrd.wearekhmer.files.model.response.FileResponse;
import com.kshrd.wearekhmer.files.service.FileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/files")
@SecurityRequirement(name = "bearerAuth")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile multipartFile){

        try {
            String fileName = fileService.uploadFile(multipartFile);
            FileResponse fileResponse = FileResponse.builder()
                    .status("200")
                    .message("upload successfully")
                    .payload(fileName)
                    .build();
            return ResponseEntity.ok(fileResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    @GetMapping("/file/filename")
    public ResponseEntity<?> getFilename(@RequestParam String name) {
        try {
            return fileService.getFile(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
