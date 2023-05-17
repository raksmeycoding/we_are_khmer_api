package com.kshrd.wearekhmer.user.controller;


import com.kshrd.wearekhmer.user.model.dto.AuthorDTO;
import com.kshrd.wearekhmer.user.model.entity.AuthorRequestTable;
import com.kshrd.wearekhmer.user.service.AuthorRequestTableService;
import com.kshrd.wearekhmer.user.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
