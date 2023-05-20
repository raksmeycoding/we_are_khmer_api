package com.kshrd.wearekhmer.userArtivities.controller;

import com.kshrd.wearekhmer.userArtivities.model.React;
import com.kshrd.wearekhmer.userArtivities.service.IReactService;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/article/react")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class ReactControllerImpl implements IReactController{


    private final IReactService reactService;
    private WeAreKhmerCurrentUser weAreKhmerCurrentUser;

    @Override
    @PostMapping("{articleId}")
    public ResponseEntity<?> createUserReactForCurrentUser(@PathVariable String articleId) {
        React react = React.builder()
                .userId(weAreKhmerCurrentUser.getUserId())
                .articleId(articleId)
                .build();
        React react1 = reactService.createUserReactForCurrentUser(react);
        return ResponseEntity.ok(react1);
    }

    @Override
    @DeleteMapping("{articleId}")
    public ResponseEntity<?> deleteUserReactForCurrentUser(@PathVariable String articleId) {
        try {
            React react = React.builder()
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .articleId(articleId)
                    .build();
            React react1 = reactService.deleteUserReactForCurrentUser(react);
            return null;
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }
}
