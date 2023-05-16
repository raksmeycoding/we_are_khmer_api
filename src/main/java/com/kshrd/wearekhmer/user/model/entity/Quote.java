package com.kshrd.wearekhmer.user.model.entity;


import lombok.*;


@AllArgsConstructor
@Builder
@Data
public class Quote {
    private String quoteId;
    private String quoteName;
    private String userId;

}
