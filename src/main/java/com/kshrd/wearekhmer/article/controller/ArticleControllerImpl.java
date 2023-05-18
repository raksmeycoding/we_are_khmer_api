package com.kshrd.wearekhmer.article.controller;


import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.article.model.request.ArticleRequest;
import com.kshrd.wearekhmer.article.service.IArticleService;
import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class ArticleControllerImpl implements IArticleController {


    private IArticleService articleService;
    private WeAreKhmerCurrentUser weAreKhmerCurrentUser;
    @Override
    @GetMapping
    public ResponseEntity<?> getAllArticles() {
        GenericResponse genericResponse;
        try {
            List<Article> articles =
                    articleService.getAllArticles();
            genericResponse = GenericResponse
                    .builder()
                    .status("200")
                    .title("success")
                    .payload(articles)
                    .build();
            return ResponseEntity.ok(genericResponse);
        } catch (Exception ex) {
            genericResponse =
                    GenericResponse
                            .builder()
                            .status("500")
                            .message(ex.getMessage())
                            .build();
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(genericResponse);
        }

    }


    @Override
    @PostMapping
    public ResponseEntity<?> insertArticle(ArticleRequest articleRequest) {
        GenericResponse genericResponse;
        try {
            Article article = Article.builder()
                    .title(articleRequest.getTitle())
                    .subTitle(articleRequest.getSubTitle())
                    .description(articleRequest.getDescription())
                    .image("Not implement yet")
                    .categoryId(articleRequest.getCategoryId())
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .build();


            Article article1 = articleService.insertArticle(article);


            genericResponse = GenericResponse.builder()
                    .title("success")
                    .message("insert successfully successfully")
                    .payload(article1)
                    .build();



            return ResponseEntity.ok(genericResponse);
        } catch (Exception ex) {
            genericResponse = GenericResponse.builder()
                    .status("500")
                    .message(ex.getMessage())
                    .title("insert failed")
                    .build();
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(genericResponse);
        }
    }
}
