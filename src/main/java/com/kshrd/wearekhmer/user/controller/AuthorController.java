package com.kshrd.wearekhmer.user.controller;


import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.user.model.dto.AuthorDTO;
import com.kshrd.wearekhmer.user.model.entity.AuthorRequestTable;
import com.kshrd.wearekhmer.user.service.AuthorRequestTableService;
import com.kshrd.wearekhmer.user.service.AuthorService;
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
    public ResponseEntity<?> getAllAuthorRequest() {
        try {
            List<AuthorRequestTable> authorRequestTable = authorRequestTableService.getAll();
            return ResponseEntity.ok(authorRequestTable);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw  new RuntimeException();
        }
    }


    @GetMapping("authorUser")
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
    public ResponseEntity<?> acceptAutorRequest(@PathVariable String userId) {
        try {
            boolean isAccepted = authorService.updateUserRequestToBeAsAuthor(userId);
            GenericResponse res;
            if(!isAccepted) {
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
