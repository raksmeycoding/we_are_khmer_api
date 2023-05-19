package com.kshrd.wearekhmer.userArtivities.controller;

import org.springframework.http.ResponseEntity;

public interface IReactController {
    ResponseEntity<?> createUserReactForCurrentUser(String articleId);
    ResponseEntity<?> deleteUserReactForCurrentUser(String articleId);
}
