package com.kshrd.wearekhmer.userArtivities.controller;

import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.userArtivities.model.React;
import com.kshrd.wearekhmer.userArtivities.service.IReactService;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.sql.SQLException;


@RestController
@RequestMapping("/api/v1/article/react")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class ReactController {


    private final IReactService reactService;
    private WeAreKhmerCurrentUser weAreKhmerCurrentUser;
    private final WeAreKhmerValidation weAreKhmerValidation;


    @PostMapping("{articleId}")
    public ResponseEntity<?> createUserReactForCurrentUser(HttpServletRequest httpServletRequest, @PathVariable String articleId) {
        try {
            weAreKhmerValidation.validateArticleId(articleId);
            React react = React.builder()
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .articleId(articleId)
                    .build();
//            ⚠️ This exception has been caught by database
//            if (!reactService.isLikeExist(articleId, weAreKhmerCurrentUser.getUserId())) {
//                ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "You already reacted on this article");
//                URI uri = URI.create(httpServletRequest.getRequestURL().toString());
//                problemDetail.setType(uri);
//                throw new ErrorResponseException(HttpStatus.FORBIDDEN, problemDetail, null);
//            }
            React react1 = reactService.createUserReactForCurrentUser(react);
            GenericResponse genericResponse = GenericResponse.builder()
                    .title("success")
                    .statusCode(201)
                    .message("You have reacted on this article successfully")
                    .payload(react1)
                    .build();
            return ResponseEntity.ok(genericResponse);
        } catch (Exception ex) {
            if (ex.getCause() instanceof SQLException) {
                System.out.println(ex.getMessage());
                String message = ex.getCause().getMessage();
                System.out.println(message);
                int startIndex = message.indexOf("ERROR:");
                int endIndex = message.indexOf("\n");
                String returnMessage = message.substring(startIndex, endIndex);
                ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, returnMessage);
                URI uri = URI.create(httpServletRequest.getRequestURL().toString());
                problemDetail.setType(uri);
                throw new ErrorResponseException(HttpStatus.FORBIDDEN, problemDetail, ex.getCause());
            }
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            URI uri = URI.create(httpServletRequest.getRequestURL().toString());
            problemDetail.setType(uri);
            throw new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR, problemDetail, null);
        }
    }


    @DeleteMapping("{articleId}")
    public ResponseEntity<?> deleteUserReactForCurrentUser(@PathVariable String articleId) {
        weAreKhmerValidation.validateArticleId(articleId);
        React react = React.builder()
                .userId(weAreKhmerCurrentUser.getUserId())
                .articleId(articleId)
                .build();
        System.out.println(reactService.isLikeExist(articleId, weAreKhmerCurrentUser.getUserId()));
        if (!reactService.isLikeExist(articleId, weAreKhmerCurrentUser.getUserId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You have not reacted on this article yet");
        }
        React react1 = reactService.deleteUserReactForCurrentUser(react);

        GenericResponse genericResponse = GenericResponse.builder()
                .title("success")
                .statusCode(200)
                .message("You have unliked this article ")
                .build();

        return ResponseEntity.ok(genericResponse);

    }
}
