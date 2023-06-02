package com.kshrd.wearekhmer.userArtivities.controller;

import com.kshrd.wearekhmer.userArtivities.model.React;
import org.springframework.http.ResponseEntity;

public interface IReactController {
    ResponseEntity<?> createUserReactForCurrentUser(String articleId);
    ResponseEntity<?> deleteUserReactForCurrentUser(String articleId);

}
