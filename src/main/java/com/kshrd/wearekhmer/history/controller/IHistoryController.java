package com.kshrd.wearekhmer.history.controller;

import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.history.model.entity.History;
import org.springframework.http.ResponseEntity;

public interface IHistoryController {
    ResponseEntity<?> getAllHistoryByCurrentId();

    ResponseEntity<?> insertHistory(String articleId);
    ResponseEntity<?> deleteHistory(String historyId);

    ResponseEntity<?> removeAllHistory();





}
