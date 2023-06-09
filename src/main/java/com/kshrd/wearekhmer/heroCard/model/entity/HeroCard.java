package com.kshrd.wearekhmer.heroCard.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HeroCard {

    private String heroCardId;

    private String categoryId;

    private String articleId;

    private Integer index;
    private String type;

}
