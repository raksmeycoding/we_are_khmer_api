package com.kshrd.wearekhmer.user.controller;


import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.user.model.dto.AuthorDTO;
import com.kshrd.wearekhmer.user.model.entity.AuthorRequestTable;
import com.kshrd.wearekhmer.user.service.AuthorRequestTableService;
import com.kshrd.wearekhmer.user.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/v1/author")
@RestController
@AllArgsConstructor
public class AuthorController {


    private final AuthorRequestTableService authorRequestTableService;
    private final AuthorService authorService;


    @GetMapping("authorRequest")
    @Operation(summary = "(Get all authors request either accept as author or not.)")
    public ResponseEntity<?> getAllAuthorRequest() {
        try {
            List<AuthorRequestTable> authorRequestTable = authorRequestTableService.getAll();
            return ResponseEntity.ok(authorRequestTable);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw  new RuntimeException();
        }
    }


    @GetMapping("authorUser ")
    @Operation(summary = "(Get all authors authors.)")
    public ResponseEntity<?> getAllAuthorUser() {
        try {
            List<AuthorDTO> authorDTOList = authorService.getAllAuthor();
            return ResponseEntity.ok(authorDTOList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }


    @PostMapping("accept/{userId}")
    @Operation(summary = "(Accept user request as author.)")
    public ResponseEntity<?> acceptAutorRequest(@PathVariable String userId) {
        try {
            String userIdAccepted = authorService.updateUserRequestToBeAsAuthor(userId);
            GenericResponse res;
            if(userIdAccepted == null) {
                res = GenericResponse.builder()
                        .status("500")
                        .message("is not accepted.")
                        .build();
                return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            res = GenericResponse.builder()
                    .status("200")
                    .message("success")
                    .build();
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }

}
