package com.kshrd.wearekhmer.history.model.response;

import com.kshrd.wearekhmer.article.model.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryResponse {

    private String historyId;
    private String userId;

    private Article article;

    private Timestamp createdAt;
}
