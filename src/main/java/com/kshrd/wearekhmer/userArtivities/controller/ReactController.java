package com.kshrd.wearekhmer.userArtivities.controller;

import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.userArtivities.model.React;
import com.kshrd.wearekhmer.userArtivities.service.IReactService;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/v1/article/react")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class ReactController implements IReactController{


    private final IReactService reactService;
    private WeAreKhmerCurrentUser weAreKhmerCurrentUser;
    private final WeAreKhmerValidation weAreKhmerValidation;

    @Override
    @PostMapping("{articleId}")
    public ResponseEntity<?> createUserReactForCurrentUser( @PathVariable String articleId) {
        weAreKhmerValidation.validateArticleId(articleId);
        React react = React.builder()
                .userId(weAreKhmerCurrentUser.getUserId())
                .articleId(articleId)
                .build();
        if(reactService.isLikeExist(articleId, weAreKhmerCurrentUser.getUserId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You already reacted on this article");

        React react1 = reactService.createUserReactForCurrentUser(react);
        GenericResponse genericResponse = GenericResponse.builder()
                .title("success")
                .status("200")
                .message("You have reacted on this article successfully")
                .payload(react1)
                .build();
        return ResponseEntity.ok(genericResponse);
    }

    @Override
    @DeleteMapping("{articleId}")
    public ResponseEntity<?> deleteUserReactForCurrentUser( @PathVariable String articleId) {
        weAreKhmerValidation.validateArticleId(articleId);


            React react = React.builder()
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .articleId(articleId)
                    .build();
        if(!reactService.isLikeExist(articleId, weAreKhmerCurrentUser.getUserId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You have not reacted on this article yet");
            React react1 = reactService.deleteUserReactForCurrentUser(react);

        GenericResponse genericResponse = GenericResponse.builder()
                .title("success")
                .status("200")
                .message("You have unliked this article ")
                .build();

        return ResponseEntity.ok(genericResponse);

    }
}
