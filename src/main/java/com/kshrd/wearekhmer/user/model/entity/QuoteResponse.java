package com.kshrd.wearekhmer.user.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class QuoteResponse {
    private String quoteId;
    private String quoteName;

}

