package com.kshrd.wearekhmer.user.model.entity;


import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Quote {
    private String quoteId;
    private String quoteName;
    private String userId;


    public void setQuteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public void setQuoteName(String quoteName) {
        this.quoteName = quoteName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
