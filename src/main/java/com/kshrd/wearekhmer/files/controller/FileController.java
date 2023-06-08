package com.kshrd.wearekhmer.files.controller;


import com.kshrd.wearekhmer.exception.CustomRuntimeException;
import com.kshrd.wearekhmer.files.model.response.FileResponse;
import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("api/v1/files")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class FileController {

    private final com.kshrd.wearekhmer.files.service.IFileService IFileService;

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


    @PostMapping(value = "/upload -file-v2", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "(You can upload file to get url)")
    public ResponseEntity<?> uploadFileVersion2(HttpServletRequest httpServletRequest, @RequestBody MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new CustomRuntimeException("File is empty");
        }
        try {
            String returnUrl = IFileService.uploadFile(multipartFile);
            return ResponseEntity.ok().body(GenericResponse.builder()
                    .title("success")
                    .message("File upload successfully!")
                    .payload(returnUrl)
                    .status("200")
                    .build());

        } catch (Exception ex) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getCause().getMessage());
            URI uri = URI.create(httpServletRequest.getRequestURI());
            problemDetail.setType(uri);
            throw new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR, problemDetail, ex.getCause());
        }
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
