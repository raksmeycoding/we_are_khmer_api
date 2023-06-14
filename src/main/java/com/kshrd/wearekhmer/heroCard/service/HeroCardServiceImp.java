package com.kshrd.wearekhmer.heroCard.service;

import com.kshrd.wearekhmer.article.repository.ArticleMapper;
import com.kshrd.wearekhmer.exception.ValidateException;
import com.kshrd.wearekhmer.heroCard.model.entity.HeroCard;
import com.kshrd.wearekhmer.heroCard.model.response.HeroCardResponse;
import com.kshrd.wearekhmer.heroCard.repository.HeroCardRepository;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class HeroCardServiceImp implements HeroCardService{
    private final HeroCardRepository heroCardRepository;

    private final ArticleMapper articleMapper;

    private final WeAreKhmerValidation weAreKhmerValidation;


    @Override
    public HeroCard insertHeroCard(HeroCard heroCard) {
            return heroCardRepository.insertHero(heroCard);
    }

    @Override
    public List<HeroCardResponse> getAllHeroCardByCategoryIdAndType(String categoryId, String type) {
        return heroCardRepository.getAllHeroCardByCategoryIdAndType(categoryId, type);
    }

    @Override
    public HeroCard deleteHeroCardByCategoryIdAndIndex(String categoryId, Integer index) {
        return heroCardRepository.deleteHeroCardByCategoryIdAndIndex(categoryId, index);
    }

    @Override
    public List<HeroCardResponse> getAllHeroCardByTypeHome() {
        return heroCardRepository.getAllHeroCardByTypeHome();
    }

    @Override
    public HeroCard deleteHeroCardByIndexAndTypeHome(Integer index) {
        if(index>0 && index<=3 ){
            if(heroCardRepository.checkIndexAndHomeHasRecord(index)){
                return heroCardRepository.deleteHeroCardByIndexAndTypeHome(index);
            }else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hero card at index : "+index+ " in type home");
            }
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Index must be (1-3)");
        }

    }

//    @Override
//    public HeroCardResponse updateHeroCardById(String heroCardId, String articleId) {
//        if(heroCardRepository.validateHeroCardId(heroCardId)){
//            if(articleMapper.isArticleExist(articleId)){
//                return heroCardRepository.updateHeroCardById(heroCardId,articleId);
//            }else {
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ArticleId : "+articleId+ " not found");
//            }
//
//        }else{
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "HeroCardId : "+heroCardId+ " not found");
//        }
//
//    }

    @Override
    public HeroCardResponse deleteHeroCardById(String HeroCardId) {
        if(!heroCardRepository.checkHeroCardIdIsExist(HeroCardId))
            throw new ValidateException("HeroCardId : "+HeroCardId+ " does not exist ", HttpStatus.NOT_FOUND,HttpStatus.NOT_FOUND.value());
        else
            return heroCardRepository.deleteHeroCardById(HeroCardId);
    }


    @Override
    public HeroCardResponse updateHeroCardById(String HeroCardId, String articleId) {
        if(!heroCardRepository.checkHeroCardIdIsExist(HeroCardId)){
            throw new ValidateException("HeroCardId : "+HeroCardId+ " does not exist ", HttpStatus.NOT_FOUND,HttpStatus.NOT_FOUND.value());
        }else{
            String categoryId = heroCardRepository.getHeroCardById(HeroCardId);
            weAreKhmerValidation.checkArticleByCategoryId(categoryId,articleId);
            return heroCardRepository.updateHeroCardById(HeroCardId,articleId);
        }

    }
}
