package com.kshrd.wearekhmer.history.service;

import com.kshrd.wearekhmer.history.model.entity.History;
import com.kshrd.wearekhmer.history.model.response.HistoryResponse;
import com.kshrd.wearekhmer.history.repository.HistoryMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HistoryImpService implements HistoryService {

    private final HistoryMapper historyMapper;

    @Override
    public History insertHistory(History history) {
        return historyMapper.insertHistory(history);
    }


    @Override
    public List<HistoryResponse> getAllHistoryByCurrentUser(String userId) {
        return historyMapper.getAllHistoryByCurrentUser(userId);
    }

    @Override
    public History deleteHistory(History history) {
        return historyMapper.deleteHistory(history);
    }

    @Override
    public List<History> removeAllHistory(History history) {
        return historyMapper.removeAllHistory(history);
    }

    @Override
    public History updateHistory(String articleId, String userId) {
        return historyMapper.updateHistory(articleId,userId);
    }

    @Override
    public Boolean getAllHistoryByCurrentId(String articleId, String userId) {
        return historyMapper.getAllHistoryCurrentId(articleId,userId);
    }



}
