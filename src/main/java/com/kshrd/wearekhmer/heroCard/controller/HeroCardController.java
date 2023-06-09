package com.kshrd.wearekhmer.heroCard.controller;

import com.kshrd.wearekhmer.article.response.ArticleResponse;
import com.kshrd.wearekhmer.bookmark.model.entity.Bookmark;
import com.kshrd.wearekhmer.bookmark.model.reponse.BookmarkResponse;
import com.kshrd.wearekhmer.bookmark.model.request.BookmarkRequest;
import com.kshrd.wearekhmer.heroCard.model.entity.HeroCard;
import com.kshrd.wearekhmer.heroCard.model.request.HeroCardRequest;
import com.kshrd.wearekhmer.heroCard.model.response.HeroCardResponse;
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

    @PostMapping
    @Operation(summary = "insert heroCard")
    public ResponseEntity<?> insertBookmark(HttpServletRequest httpServletRequest, @Validated @RequestBody HeroCardRequest heroCardRequest) {
        if (!(heroCardRequest.getIndex() > 0 && heroCardRequest.getIndex() <= 3)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "index field only contain (1-3)");
        }

        weAreKhmerValidation.checkHeroCard(heroCardRequest.getCategoryId(), heroCardRequest.getIndex(), heroCardRequest.getArticleId());
        weAreKhmerValidation.validateCategoryId(heroCardRequest.getCategoryId());
        weAreKhmerValidation.checkArticleByCategoryId(heroCardRequest.getCategoryId(), heroCardRequest.getArticleId());
        weAreKhmerValidation.validate3ArticleInCategoryId(heroCardRequest.getCategoryId());

        try {
            GenericResponse genericResponse;
            HeroCard heroCard = HeroCard.builder()
                    .index(heroCardRequest.getIndex())
                    .categoryId(heroCardRequest.getCategoryId())
                    .articleId(heroCardRequest.getArticleId())
                    .type(heroCardRequest.getType().toLowerCase())
                    .build();


            if (!(heroCardRequest.getType().equalsIgnoreCase("home") || heroCardRequest.getType().equalsIgnoreCase("category"))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "type field only contain (home and category) only");
            }

            HeroCard heroCard1 = heroCardServiceImp.insertHeroCard(heroCard);
            genericResponse =
                    GenericResponse.builder()
                            .status("200")
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

    @GetMapping("/{categoryId}")
    @Operation(summary = "get all hero card")
    public ResponseEntity<?> getAllHeroCard(@PathVariable("categoryId") String categoryId) {
        GenericResponse genericResponse;
            weAreKhmerValidation.validateCategoryId(categoryId);

            List<HeroCardResponse> heroCardResponses = heroCardServiceImp.getAllHeroCardByCategoryId(categoryId);
        System.out.println("List: "+ heroCardResponses);
            genericResponse = GenericResponse.builder()
                    .status("200")
                    .payload(heroCardResponses)
                    .title("success")
                    .message("You have successfully got all bookmark recorded")
                    .build();
            return ResponseEntity.ok(genericResponse);

    }

    @DeleteMapping("/{categoryId}/{index}")
    @Hidden
    @Operation(summary = "delete hero card by categoryId and Index")
    public ResponseEntity<?> deleteHeroCardByCategoryIdAndIndex(@PathVariable("categoryId") String category, @PathVariable("index") Integer index){

        if(weAreKhmerValidation.checkIsCategoryIdOutOfIndex(category,index)){
            HeroCard heroCard = heroCardServiceImp.deleteHeroCardByCategoryIdAndIndex(category, index);
            GenericResponse genericResponse = GenericResponse.builder()
                    .status("200")
                    .title("success")
                    .message("You have successfully delete heroCard that is in categoryId : "+category+ " and index : "+index)
                    .payload(heroCard)
                    .build();
            return ResponseEntity.ok(genericResponse);
        }else{

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "categoryId : "+category+ " and Index : "+index + " does not exists");
        }
    }

    @GetMapping("/home")
    @Operation(summary = "get all hero card by homepage")
    public ResponseEntity<?> getAllHeroCardByHome(){
        List<HeroCardResponse> heroCardResponses = heroCardServiceImp.getAllHeroCardByHome();

        GenericResponse genericResponse = GenericResponse.builder()
                .status("200")
                .title("success")
                .message("You have successfully get all hero card by homepage")
                .payload(heroCardResponses)
                .build();

        return ResponseEntity.ok(genericResponse);

    }

    @DeleteMapping("/home/{index}")
    @Hidden
    @Operation(summary = "Delete hero card by Index and Type (home) ")
    public ResponseEntity<?> deleteHeroCardByIndexAndTypeHome(@PathVariable Integer index){
        HeroCard heroCard = heroCardServiceImp.deleteHeroCardByIndexAndTypeHome(index);
                GenericResponse genericResponse = GenericResponse.builder()
                        .status("200")
                        .title("success")
                        .message("You have successfully delete hero card at index : "+index+ " in Type home")
                        .payload(heroCard)
                        .build();

                return ResponseEntity.ok(genericResponse);
    }

    @PutMapping("/heroCardId")
    @Operation(summary = "Update hero card by heroCardId")
    public ResponseEntity<?> updateHeroCardByHeroCardId(@RequestParam("heroCardId") String heroCardId, @RequestParam("articleId") String articleId){
        HeroCardResponse heroCardResponse = heroCardServiceImp.updateHeroCardById(heroCardId, articleId);
        GenericResponse genericResponse = GenericResponse.builder()
                .status("200")
                .title("success")
                .message("You have successfully updated this hero card")
                .payload(heroCardResponse)
                .build();
        return ResponseEntity.ok(genericResponse);
    }

}
