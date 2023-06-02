package com.kshrd.wearekhmer.userArtivities.controller;


import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.userArtivities.model.navbar.Navbar;
import com.kshrd.wearekhmer.userArtivities.model.navbar.NavbarOrdering;
import com.kshrd.wearekhmer.userArtivities.service.NavbarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/order-navbar")
public class OrderNavbarController {

    private final NavbarService navbarService;


    @Operation(summary = "(Admin acc can post to order navbar)")
    @PostMapping
    ResponseEntity<?> insertOrderNavbar(@RequestBody List<Navbar> navbar) {
        for (Navbar n : navbar) {
            Navbar navbar1 = navbarService.updateNavbar(n);
            System.out.println(navbar1);
        }
        return null;
    }



    @Operation(summary = "(get public navbar)")
    @GetMapping
    ResponseEntity<?> getOrderNavbar() {
        try {
            List<Navbar> navbarList = navbarService.getNavbar();
            GenericResponse genericResponse = GenericResponse.builder()
                    .message("Get data successfully.")
                    .title("success")
                    .status("200")
                    .payload(navbarList)
                    .build();
            return ResponseEntity.ok(genericResponse);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }
}
