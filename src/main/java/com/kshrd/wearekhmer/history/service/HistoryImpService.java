package com.kshrd.wearekhmer.history.service;

import com.kshrd.wearekhmer.history.model.entity.History;
import com.kshrd.wearekhmer.history.model.response.HistoryResponse;
import com.kshrd.wearekhmer.history.repository.HistoryMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HistoryImpService implements IHistoryService{

    private final HistoryMapper historyMapper;
    @Override
    public List<History> getAllHistory() {
        return historyMapper.getAllHistory();
    }

    @Override
    public History insertHistory(History history) {
        return historyMapper.insertHistory(history);
    }

    @Override
    public History deleteHistory(History history) {
        return historyMapper.deleteHistory(history);
    }

    @Override
    public List<HistoryResponse> getAllHistoryByCurrentUser(String userId) {
        return historyMapper.getAllHistoryByCurrentUser(userId);
    }





}
