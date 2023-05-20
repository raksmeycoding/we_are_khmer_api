package com.kshrd.wearekhmer.history.service;

import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.history.model.entity.History;
import com.kshrd.wearekhmer.history.model.response.HistoryResponse;

import java.util.List;

public interface IHistoryService {
    List<History> getAllHistory();

    History insertHistory(History history);

    History deleteHistory(History history);

    List<HistoryResponse> getAllHistoryByCurrentUser(String userId);

}
