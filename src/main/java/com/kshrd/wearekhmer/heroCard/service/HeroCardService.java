package com.kshrd.wearekhmer.heroCard.service;

import com.kshrd.wearekhmer.heroCard.model.entity.HeroCard;
import com.kshrd.wearekhmer.heroCard.model.request.HeroCardRequest;
import com.kshrd.wearekhmer.heroCard.model.response.HeroCardResponse;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface HeroCardService {
    HeroCard insertHeroCard(HeroCard heroCard);

    List<HeroCardResponse> getAllHeroCardByCategoryId(String categoryId);

    HeroCard deleteHeroCardByCategoryIdAndIndex(String categoryId, Integer index);

    List<HeroCardResponse> getAllHeroCardByHome();

    HeroCard deleteHeroCardByIndexAndTypeHome(Integer index);

    HeroCardResponse updateHeroCardById(String heroCardId, String articleId);
}
