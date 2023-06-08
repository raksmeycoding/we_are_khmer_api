package com.kshrd.wearekhmer.user.controller;


import com.kshrd.wearekhmer.exception.CustomRuntimeException;
import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.user.model.dto.AuthorDTO;
import com.kshrd.wearekhmer.user.model.entity.AuthorRequestTable;
import com.kshrd.wearekhmer.user.repository.AuthorRepository;
import com.kshrd.wearekhmer.user.service.AuthorRequestTableService;
import com.kshrd.wearekhmer.user.service.AuthorService;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RequestMapping("/api/v1/author")
@SecurityRequirement(name = "bearerAuth")
@RestController
@AllArgsConstructor
public class AuthorController {


    private final AuthorRequestTableService authorRequestTableService;
    private final AuthorService authorService;

    private final AuthorRepository authorRepository;

    private final WeAreKhmerValidation weAreKhmerValidation;

    private WeAreKhmerCurrentUser weAreKhmerCurrentUser;


    @GetMapping("authorRequest")
    @Operation(summary = "(Get all authors request either accept as author or not.)")
    public ResponseEntity<?> getAllUserRequestAsAuthorEitherAcceptOrNot() {
        try {
            List<AuthorRequestTable> authorRequestTable = authorRequestTableService.getAll();
            return ResponseEntity.ok(authorRequestTable);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        }
    }


    @GetMapping("authorUser")
    @Operation(summary = "(Get all only authors user.)")
    public ResponseEntity<?> getAllAuthorUser() {
        try {
            List<AuthorDTO> authorDTOList = authorService.getAllAuthor();
            return ResponseEntity.ok(authorDTOList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }


    @GetMapping("/{authorId}")
    @Operation(summary = "(User view author profile)")
    public ResponseEntity<?> getAllAuthorById(HttpServletRequest httpServletRequest, @PathVariable String authorId) {
        AuthorDTO authorDTO = authorService.getAllAuthorById(authorId);
        if ( authorDTO == null) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Author had been not found!");
            problemDetail.setType(URI.create(httpServletRequest.getRequestURL().toString()));
            throw new ErrorResponseException(HttpStatus.NOT_FOUND, problemDetail, null);
        }
        return ResponseEntity.ok().body(GenericResponse.builder()
                .status("200")
                .title("success")
                .message("Get author profile successfully.")
                .payload(authorDTO)
                .build());
    }


    @PostMapping("accept/{userId}")
    @Operation(summary = "(Accept user request as author.)")
    public ResponseEntity<?> updateUserRequestToBeAsAuthor(@PathVariable String userId) {
        String hasRoleAuthor = authorRepository.userAlreadyAuthor(userId);
        System.out.println(hasRoleAuthor);
        if (!weAreKhmerValidation.isAdmin()) {
            throw new CustomRuntimeException("You are not admin.");
        }
        if (hasRoleAuthor != null && hasRoleAuthor.equalsIgnoreCase("ROLE_AUTHOR")) {
            throw new CustomRuntimeException("User Already Exist.");
        }
        String userIdAccepted = authorService.updateUserRequestToBeAsAuthor(userId);
        GenericResponse res;
        if (userIdAccepted == null) {
            res = GenericResponse.builder()
                    .status("500")
                    .message("is not accepted.")
                    .build();
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        res = GenericResponse.builder()
                .status("200")
                .message("User is accepted as author.")
                .build();
        return ResponseEntity.ok(res);

    }

    @GetMapping("/profile")
    @Operation(summary = "(Author view own profile ( only Author) )")
    public ResponseEntity<?> getCurrentAuthor() {
        weAreKhmerValidation.checkAuthorExist(weAreKhmerCurrentUser.getUserId());
        AuthorDTO authorDTO = authorService.getCurrentAuthorById(weAreKhmerCurrentUser.getUserId());

        return ResponseEntity.ok().body(GenericResponse.builder()
                .status("200")
                .title("success")
                .message("Get author profile successfully.")
                .payload(authorDTO)
                .build());
    }

}
