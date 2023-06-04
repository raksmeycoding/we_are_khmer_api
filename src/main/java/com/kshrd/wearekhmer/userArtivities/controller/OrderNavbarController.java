package com.kshrd.wearekhmer.userArtivities.controller;


import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.userArtivities.model.navbar.Navbar;
import com.kshrd.wearekhmer.userArtivities.model.navbar.NavbarResponse;
import com.kshrd.wearekhmer.userArtivities.service.NavbarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/order-navbar")
public class OrderNavbarController {

    private final NavbarService navbarService;


    @Operation(summary = "(Admin acc can post to order navbar)")
    @PostMapping
    ResponseEntity<?> insertOrderNavbar(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody List<Navbar> navbar) {
        try {
            List<Navbar> listUpdatedNavbar = new ArrayList<>();
            for (Navbar n : navbar) {
                Navbar navbar1 = navbarService.updateNavbar(n);
                listUpdatedNavbar.add(navbar1);
            }
            if (listUpdatedNavbar.get(0) == null) {
                return ResponseEntity.ok(GenericResponse.builder()
                        .status("200")
                        .message("Successfully, but there are no fields got updated please make sure you inputted the correct id.")
                        .title("success")
                        .build());
            }
            return ResponseEntity.ok().body(GenericResponse.builder()
                    .title("success")
                    .message("Navbar has been updated successfully.")
                    .payload(listUpdatedNavbar)
                    .status("200")
                    .build());
        } catch (Exception ex) {
            if (ex.getCause() instanceof SQLException) {
                if (((SQLException) ex.getCause()).getSQLState().equals("23505")) {
                    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "Has some value duplicated");
                    URI myUri = URI.create(httpServletRequest.getRequestURL().toString());
                    problemDetail.setType(myUri);
                    throw new ErrorResponseException(HttpStatus.CONFLICT, problemDetail, ex.getCause());
                }
            }

            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            URI myUri = URI.create(httpServletRequest.getRequestURL().toString());
            problemDetail.setType(myUri);
            throw new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR, problemDetail, ex.getCause());
        }
    }


    @Operation(summary = "(get public navbar)")
    @GetMapping
    ResponseEntity<?> getOrderNavbar() {
        List<NavbarResponse> navbarList = navbarService.getNavbarAsNavbarResponse();
        GenericResponse genericResponse = GenericResponse.builder()
                .message("Get data successfully.")
                .title("success")
                .status("201")
                .payload(navbarList)
                .build();
        return new ResponseEntity<>(genericResponse, HttpStatus.CREATED);

    }
}
