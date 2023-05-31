package com.kshrd.wearekhmer.files.controller;


import com.kshrd.wearekhmer.exception.CustomRuntimeException;
import com.kshrd.wearekhmer.files.model.response.FileResponse;
import com.kshrd.wearekhmer.files.service.IFileService;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/files")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class FileController {

    private final IFileService IFileService;

    private final WeAreKhmerValidation weAreKhmerValidation;



    @PostMapping(value = "/file/{type}/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "You can chose either id of category, user, or article. type = type of category, article, or user image")
    public ResponseEntity<?> uploadFile(@PathVariable String type, @PathVariable String id, @RequestParam MultipartFile multipartFile) {
        weAreKhmerValidation.validateTypeFileUpload(type);
        weAreKhmerValidation.validateTypeFileUploadAndIdWithType(type, id);
        ResponseEntity<?> result;
        try {
            String fileName = IFileService.uploadFileV2(multipartFile, type, id);
            FileResponse<Object> fileResponse = FileResponse.builder()
                    .status("200")
                    .message("upload successfully")
                    .payload(fileName)
                    .build();
            result = ResponseEntity.ok(fileResponse);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }


        return result;
    }


    @GetMapping("/file/filename")
    public ResponseEntity<?> getFilename(@RequestParam String name) {
        try {
            return IFileService.getFile(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
