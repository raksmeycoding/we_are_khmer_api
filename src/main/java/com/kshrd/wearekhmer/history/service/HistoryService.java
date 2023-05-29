package com.kshrd.wearekhmer.history.service;

import com.kshrd.wearekhmer.history.model.entity.History;
import com.kshrd.wearekhmer.history.model.response.HistoryResponse;

import java.util.List;

public interface HistoryService {

    History insertHistory(History history);


    List<HistoryResponse> getAllHistoryByCurrentUser(String userId);


    History deleteHistory(History history);

    List<History> removeAllHistory(History history);

    History updateHistory(String articleId, String userId);

    Boolean getAllHistoryByCurrentId(String articleId, String userId);


}
