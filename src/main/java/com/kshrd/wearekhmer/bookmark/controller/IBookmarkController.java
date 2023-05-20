package com.kshrd.wearekhmer.bookmark.controller;

import org.springframework.http.ResponseEntity;

public interface IBookmarkController {
    ResponseEntity<?> getAllBookmarkCurrentId();

    ResponseEntity<?> insertBookmark(String articleId);
    ResponseEntity<?> deleteBookmark(String articleId);
}
