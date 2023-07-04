package com.kshrd.wearekhmer.heroCard.controller;

import com.kshrd.wearekhmer.article.response.ArticleResponse;
import com.kshrd.wearekhmer.bookmark.model.entity.Bookmark;
import com.kshrd.wearekhmer.bookmark.model.reponse.BookmarkResponse;
import com.kshrd.wearekhmer.bookmark.model.request.BookmarkRequest;
import com.kshrd.wearekhmer.exception.ValidateException;
import com.kshrd.wearekhmer.heroCard.model.entity.HeroCard;
import com.kshrd.wearekhmer.heroCard.model.request.HeroCardRequest;
import com.kshrd.wearekhmer.heroCard.model.response.HeroCardResponse;
import com.kshrd.wearekhmer.heroCard.repository.HeroCardRepository;
import com.kshrd.wearekhmer.heroCard.service.HeroCardService;
import com.kshrd.wearekhmer.heroCard.service.HeroCardServiceImp;
import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.utils.serviceClassHelper.ServiceClassHelper;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import io.swagger.models.auth.In;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.Path;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/heroCard")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class HeroCardController {

    private final HeroCardServiceImp heroCardServiceImp;
    private WeAreKhmerValidation weAreKhmerValidation;
    private ServiceClassHelper classHelper;

    private final HeroCardRepository heroCardRepository;

    @PostMapping
    @Operation(summary = "Insert hero card for admin")
    public ResponseEntity<?> insertBookmark(HttpServletRequest httpServletRequest, @Validated @RequestBody HeroCardRequest heroCardRequest) {
        if (!(heroCardRequest.getIndex() > 0 && heroCardRequest.getIndex() <= 3)) {
            throw new ValidateException("index field only contain (1-3)",HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
        }
        weAreKhmerValidation.validateHeroType(heroCardRequest.getType());
        weAreKhmerValidation.validateCategoryId(heroCardRequest.getCategoryId());
        weAreKhmerValidation.checkArticleByCategoryId(heroCardRequest.getCategoryId(), heroCardRequest.getArticleId());
        weAreKhmerValidation.validateHeroCardIndexExist(heroCardRequest.getIndex(), heroCardRequest.getType(), heroCardRequest.getCategoryId());
        weAreKhmerValidation.checkArticleAlreadyExistInHeroCard(heroCardRequest.getCategoryId(), heroCardRequest.getArticleId(),heroCardRequest.getType());

        if(heroCardRequest.getType().equals("home")) {
            if(heroCardRepository.checkIndexAndHomeHasRecord(heroCardRequest.getIndex()))
                throw new ValidateException("Index is not available ", HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.value());
        }



        try {
            GenericResponse genericResponse;
            HeroCard heroCard = HeroCard.builder()
                    .index(heroCardRequest.getIndex())
                    .categoryId(heroCardRequest.getCategoryId())
                    .articleId(heroCardRequest.getArticleId())
                    .type(heroCardRequest.getType().toLowerCase())
                    .build();


//            if (!(heroCardRequest.getType().equalsIgnoreCase("home") || heroCardRequest.getType().equalsIgnoreCase("category"))) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "type field only contain (home and category) only");
//            }

            HeroCard heroCard1 = heroCardServiceImp.insertHeroCard(heroCard);
            genericResponse =
                    GenericResponse.builder()
                            .statusCode(200)
                            .payload(heroCard1)
                            .title("success")
                            .message("You have insert article to hero card successfully")
                            .build();
            return ResponseEntity.ok(genericResponse);
        } catch (Exception ex) {
            if (ex.getCause() instanceof SQLException) {
                if (((SQLException) ex.getCause()).getSQLState().equals("23505")) {
                    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "Index number has already existed.");
                    problemDetail.setType(URI.create(httpServletRequest.getRequestURL().toString()));
                    throw new ErrorResponseException(HttpStatus.CONFLICT, problemDetail, null);
                }
            }
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            URI uri = URI.create(httpServletRequest.getRequestURL().toString());
            problemDetail.setType(uri);
            throw new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR, problemDetail, null);
        }
    }

    @GetMapping("/{type}")
    @Operation(summary = "Get hero card by categoryId and type for admin")
    public ResponseEntity<?> getHeroCardByCategoryIdAndType(@RequestParam("categoryId") String categoryId, @PathVariable("type") String type) {
        GenericResponse genericResponse;
            weAreKhmerValidation.validateCategoryId(categoryId);
            weAreKhmerValidation.validateHeroType(type);

            List<HeroCardResponse> heroCardResponses = heroCardServiceImp.getAllHeroCardByCategoryIdAndType(categoryId,type);

            if(heroCardResponses.size()>0){
                genericResponse = GenericResponse.builder()
                        .statusCode(200)
                        .payload(heroCardResponses)
                        .title("success")
                        .message("You have successfully got hero card in categoryId : "+categoryId+ " and its type is "+type)
                        .build();
                return ResponseEntity.ok(genericResponse);
            }else{
                throw new ValidateException("There's no hero card in categoryId : "+categoryId+ " and type : "+type, HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
            }



    }

    @DeleteMapping("/")
    @Hidden
    @Operation(summary = "delete hero card by categoryId and Index")
    public ResponseEntity<?> deleteHeroCardByCategoryIdAndIndex(@RequestParam("categoryId") String category, @RequestParam("index") Integer index){

        if(weAreKhmerValidation.checkIsCategoryIdOutOfIndex(category,index)){
            HeroCard heroCard = heroCardServiceImp.deleteHeroCardByCategoryIdAndIndex(category, index);
            GenericResponse genericResponse = GenericResponse.builder()
                    .statusCode(200)
                    .title("success")
                    .message("You have successfully delete heroCard that is in categoryId : "+category+ " and index : "+index)
                    .payload(heroCard)
                    .build();
            return ResponseEntity.ok(genericResponse);
        }else{

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "categoryId : "+category+ " and Index : "+index + " does not exists");
        }
    }

    @GetMapping()
    @Operation(summary = "Get hero card for homepage for admin")
    public ResponseEntity<?> getAllHeroCardByHome(){
        List<HeroCardResponse> heroCardResponses = heroCardServiceImp.getAllHeroCardByTypeHome();

        if(heroCardResponses.size()>0){
            GenericResponse genericResponse = GenericResponse.builder()
                    .statusCode(200)
                    .title("success")
                    .message("You have successfully get hero card for homepage")
                    .payload(heroCardResponses)
                    .build();

            return ResponseEntity.ok(genericResponse);
        }
        GenericResponse genericResponse = GenericResponse.builder()
                .statusCode(200)
                .title("success")
                .message("There's no hero card for homepage")
                .build();
        return ResponseEntity.ok(genericResponse);


    }


    @PutMapping
    @Operation(summary = "Update hero card for admin")
    public ResponseEntity<?> updateHeroCardByHeroCardId(@RequestParam("HeroCardId") String heroCardId, @RequestParam("articleId") String articleId){
        HeroCardResponse heroCardResponse = heroCardServiceImp.updateHeroCardById(heroCardId, articleId);
        GenericResponse genericResponse = GenericResponse.builder()
                .statusCode(200)
                .title("success")
                .message("You have successfully updated this hero card")
                .payload(heroCardResponse)
                .build();
        return ResponseEntity.ok(genericResponse);
    }

    @DeleteMapping
    @Operation(summary = "Delete hero card for admin")
    public ResponseEntity<?> deleteHeroCardById(@RequestParam("HeroCardId") String heroCardId){


        HeroCardResponse heroCardResponse = heroCardServiceImp.deleteHeroCardById(heroCardId);

        GenericResponse genericResponse = GenericResponse.builder()
                .statusCode(200)
                .title("success")
                .message("You have successfully delete this hero card")
                .payload(heroCardResponse)
                .build();
        return ResponseEntity.ok(genericResponse);
    }

}
