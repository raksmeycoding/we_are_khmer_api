package com.kshrd.wearekhmer.history.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryRequest {
    private String userId;
    private String  articleId;
}
