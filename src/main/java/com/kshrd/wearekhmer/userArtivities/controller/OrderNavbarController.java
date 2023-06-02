package com.kshrd.wearekhmer.userArtivities.controller;


import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.userArtivities.model.navbar.Navbar;
import com.kshrd.wearekhmer.userArtivities.service.NavbarService;
import io.jsonwebtoken.lang.Assert;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/order-navbar")
public class OrderNavbarController {

    private final NavbarService navbarService;


    @Operation(summary = "(Admin acc can post to order navbar)")
    @PostMapping
    ResponseEntity<?> insertOrderNavbar(HttpServletRequest httpServletRequest, @RequestBody List<Navbar> navbar) {
//        URI type = null;
        try {
//            type = new URI(httpServletRequest.getRequestURI());
            List<Navbar> listUpdatedNavbar = new ArrayList<>();
            for (Navbar n : navbar) {
                Navbar navbar1 = navbarService.updateNavbar(n);
                listUpdatedNavbar.add(navbar1);
            }
            return ResponseEntity.ok().body(listUpdatedNavbar);
        } catch (Exception ex) {
            if (ex.getCause() instanceof SQLException) {
                if (((SQLException) ex.getCause()).getSQLState().equals("23505")) {
                    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "Has some value duplicated");
                    ErrorResponseException exception = new ErrorResponseException(HttpStatus.CONFLICT, problemDetail, ex.getCause());
                    throw exception;

                }
            }
            throw new RuntimeException();
        }
    }


    @Operation(summary = "(get public navbar)")
    @GetMapping
    ResponseEntity<?> getOrderNavbar() {
        List<Navbar> navbarList = navbarService.getNavbar();
        GenericResponse genericResponse = GenericResponse.builder()
                .message("Get data successfully.")
                .title("success")
                .status("201")
                .payload(navbarList)
                .build();
        return new ResponseEntity<>(genericResponse, HttpStatus.CREATED);

    }
}
