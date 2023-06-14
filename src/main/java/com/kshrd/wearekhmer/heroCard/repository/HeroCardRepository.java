package com.kshrd.wearekhmer.heroCard.repository;


import com.kshrd.wearekhmer.bookmark.model.entity.Bookmark;
import com.kshrd.wearekhmer.heroCard.controller.HeroCardController;
import com.kshrd.wearekhmer.heroCard.model.entity.HeroCard;
import com.kshrd.wearekhmer.heroCard.model.request.HeroCardRequest;
import com.kshrd.wearekhmer.heroCard.model.response.HeroCardResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface HeroCardRepository {

    @Results(id = "heroCard", value = {
            @Result(property = "heroCardId", column = "hero_card_id"),
            @Result(property = "index", column = "index"),
            @Result(property = "articleId", column = "article_id"),
            @Result(property = "categoryId", column = "category_id"),
            @Result(property = "type", column = "type")
    })
    @Select("""
            INSERT INTO hero_card_tb (index, category_id, article_id, type) VALUES (#{index}, #{categoryId}, #{articleId}, cast(#{type} as heroType))  returning *;
            """)
    HeroCard insertHero(HeroCard heroCard);

    @Select("""
            SELECT EXISTS(SELECT 1 FROM article_tb WHERE category_id = #{categoryId} AND article_id = #{articleId})
            """)
    boolean checkArticleIsExistInCategory(String categoryId, String articleId);


    @Select("""
            SELECT EXISTS(SELECT 1 FROM hero_card_tb WHERE index = #{index});
            """)
    boolean checkIsIndexExist(Integer index);


    @Select("""
            SELECT EXISTS(SELECT 1 FROM hero_card_tb WHERE index = #{index} AND category_id = #{categoryId}
                                                       AND article_id = #{articleId})
            """)
    boolean checkHeroCardIsExist(String categoryId, Integer index, String articleId);


    @Select(
            """
            SELECT count(article_id)>=3 from hero_card_tb WHERE category_id = #{categoryId}
            """
    )
    boolean articleInHeroCard(String categoryId);

    @Select("""
            SELECT EXISTS(SELECT 1 FROM hero_card_tb WHERE category_id = #{categoryId} AND index = #{index});
            """)
    boolean checkIsIndexOfCategoryIdFull(String categoryId, Integer index);



    @Select("""
            SELECT * FROM hero_card_tb WHERE category_id = #{categoryId} AND type = cast(#{type} as heroType)
            """)
    @Results(
            id = "heroCardResponse", value = {
            @Result(property = "heroCardId", column = "hero_card_id"),
            @Result(property = "categoryId", column = "category_id"),
            @Result(property = "articleResponse", column = "article_id",  many = @Many(select = "com.kshrd.wearekhmer.article.repository.ArticleMapper.getArticleById")),
            @Result(property = "index", column = "index"),
            @Result(property = "type", column = "type")   ,
    }
    )

    List<HeroCardResponse> getAllHeroCardByCategoryIdAndType(String categoryId, String type);

    @Select("""
            DELETE FROM hero_card_tb WHERE category_id = #{categoryId} AND index = #{index}
            """)
    HeroCard deleteHeroCardByCategoryIdAndIndex(String categoryId, Integer index);


    @Select("""
            SELECT * FROM hero_card_tb WHERE type = 'home'
            """)
    @Result(property = "heroCardId", column = "hero_card_id")
    @Result(property = "categoryId", column = "category_id")
    @Result(property = "articleResponse", column = "article_id",  many = @Many(select = "com.kshrd.wearekhmer.article.repository.ArticleMapper.getArticleById"))
    @Result(property = "index", column = "index")
    @Result(property = "type", column = "type")
    List<HeroCardResponse> getAllHeroCardByTypeHome();

    @Select("""
            DELETE FROM hero_card_tb WHERE index = #{index} AND type = 'home'
            """)
    HeroCard deleteHeroCardByIndexAndTypeHome(Integer index);

    @Select("""
            SELECT EXISTS(SELECT 1 FROM hero_card_tb WHERE index = #{index} AND type = 'home');
            """)
    boolean checkIndexAndHomeHasRecord(Integer index);

//    @Select("""
//            UPDATE hero_card_tb
//            SET article_id = #{articleId}
//            WHERE hero_card_id = #{heroCardId} returning *;
//            """)
//    @ResultMap("heroCardResponse")
//    HeroCardResponse updateHeroCardById(String heroCardId, String articleId);


    @Select("""
            SELECT EXISTS(SELECT 1 FROM hero_card_tb WHERE hero_card_id = #{heroCardId})
            """)
    boolean validateHeroCardId(String heroCardId);

    @Select("""
SELECT EXISTS(SELECT 1 FROM hero_card_tb WHERE index = #{index}  AND category_id = #{categoryId} AND type= cast(#{type} as heroType))
            """)
    boolean validateHeroCardIndexExist(Integer index, String type, String categoryId );


    @Select("""
            SELECT EXISTS(SELECT 1 FROM hero_card_tb WHERE category_id = #{categoryId} AND article_id = #{articleId} AND type = cast(#{type} as heroType))
            """)
    boolean checkArticleAlreadyExistInHeroCard(String categoryId, String articleId, String type);

    @Select("""
            SELECT EXISTS(SELECT 1 FROM hero_card_tb WHERE hero_card_id = #{HeroCardId})
            """)
    boolean checkHeroCardIdIsExist(String HeroCardId );

    @Select("""
            DELETE FROM hero_card_tb WHERE hero_card_id = #{HeroCardId} returning *
            """)
    @ResultMap("heroCardResponse")
    HeroCardResponse deleteHeroCardById(String HeroCardId);


    @Select("""
            UPDATE hero_card_tb 
            SET article_id = #{articleId}
            WHERE hero_card_id = #{HeroCardId} returning *
            """)
    @ResultMap("heroCardResponse")
    HeroCardResponse updateHeroCardById(String HeroCardId, String articleId);

    @Select("""
            SELECT category_id FROM hero_card_tb WHERE hero_card_id = #{HeroCardId}
            """)
    String getHeroCardById(String HeroCardId);

}
