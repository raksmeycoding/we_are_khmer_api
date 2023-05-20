package com.kshrd.wearekhmer.article.controller;


import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.article.model.request.ArticleRequest;
import com.kshrd.wearekhmer.article.model.request.ArticleUpdateRequest;
import com.kshrd.wearekhmer.article.response.ArticleResponse;
import com.kshrd.wearekhmer.article.service.IArticleService;
import com.kshrd.wearekhmer.files.config.FileConfig;
import com.kshrd.wearekhmer.files.service.IFileService;
import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class ArticleControllerImpl implements IArticleController {


    private IArticleService articleService;
    private WeAreKhmerCurrentUser weAreKhmerCurrentUser;

    private final IFileService fileService;

    private final FileConfig fileConfig;

    @Override
    @GetMapping
    @Operation(summary = "get all articles")
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
    public ResponseEntity<?> getAllArticleForCurrentUser() {
        GenericResponse genericResponse;
        try {
            List<ArticleResponse> articleResponses = articleService.getArticlesForCurrentUser(weAreKhmerCurrentUser.getUserId());
            genericResponse =
                    GenericResponse.builder()
                            .title("success")
                            .message("request successfully")
                            .payload(articleResponses)
                            .build();

            return ResponseEntity.ok(genericResponse);

        } catch (Exception ex) {
            genericResponse = GenericResponse.builder()
                    .status("500")
                    .message(ex.getMessage())
                    .title("internal server error!")
                    .build();
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(genericResponse);
        }

    }

    @Override
    @Operation(summary = "get all articles (current for current user)")
    @GetMapping("currentUser")
    public ResponseEntity<?> getAllArticlesForCurrentUser() {
        GenericResponse genericResponse;
        try {
            String currentUerId = weAreKhmerCurrentUser.getUserId();
            List<ArticleResponse> articles = articleService.getArticlesForCurrentUser(currentUerId);
            genericResponse = GenericResponse.builder()
                    .status("200")
                    .message("request successfully")
                    .payload(articles)
                    .title("success")
                    .build();
            return ResponseEntity.ok(genericResponse);

        } catch (Exception ex) {
            genericResponse =
                    GenericResponse.builder()
                            .title("failed")
                            .message(ex.getMessage())
                            .status("500")
                            .build();

            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(genericResponse);
        }
    }

    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "insert article (for current user).")
    public ResponseEntity<?> insertArticle(@RequestParam MultipartFile multipartFile, ArticleRequest articleRequest) {
        GenericResponse genericResponse;
        try {

//            processing image and article
            String imageName = fileService.uploadFile(multipartFile);
//            save image to desired location
//            Generate the image URL
            String generateImageUrl;
            generateImageUrl = "http://localhost:8080/api/v1/files/file/filename?name=" + imageName;
//            Save image url to article object

            Article article = Article.builder()
                    .title(articleRequest.getTitle())
                    .subTitle(articleRequest.getSubTitle())
                    .description(articleRequest.getDescription())
                    .image(imageName)
                    .categoryId(articleRequest.getCategoryId())
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .build();


            Article article1 = articleService.insertArticle(article);
            article1.setImage(generateImageUrl);


            genericResponse = GenericResponse.builder()
                    .title("success")
                    .status("200")
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

    @Override
    @GetMapping("{articleId}")
    @Operation(summary = "get article by id")
    public ResponseEntity<?> getArticleById(@PathVariable String articleId) {
        GenericResponse genericResponse;
        try {
            ArticleResponse article = articleService.getArticleById(articleId);
            genericResponse = GenericResponse.builder()
                    .status("200")
                    .title("success")
                    .message("request successfully")
                    .payload(article)
                    .build();
            return ResponseEntity.ok(genericResponse);
        } catch (Exception ex) {
            genericResponse = GenericResponse.builder()
                    .title("request failed")
                    .status("500")
                    .message(ex.getMessage())
                    .build();
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(genericResponse);
        }
    }


    @Override
    @PutMapping
    @Operation(summary = "update article by id (for current user).")
    public ResponseEntity<?> updateArticle(ArticleUpdateRequest article) {
        GenericResponse genericResponse;
        try {
            Article article1 = Article.builder()
                    .articleId(article.getArticleId())
                    .title(article.getTitle())
                    .subTitle(article.getSubTitle())
                    .description(article.getDescription())
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .categoryId(article.getCategoryId())
                    .build();
            Article article2 = articleService.updateArticle(article1);
            genericResponse = GenericResponse.builder()
                    .status("200")
                    .title("success")
                    .message("update successfully.")
                    .payload(article2)
                    .build();
            return ResponseEntity.ok(genericResponse);
        } catch (Exception ex) {
            genericResponse =
                    GenericResponse.builder()
                            .title("failed")
                            .message(ex.getMessage())
                            .status("500")
                            .build();
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(genericResponse);
        }

    }


    @Override
    @DeleteMapping("{articleId}")
    @Operation(summary = "delete article by id (for current user).")
    public ResponseEntity<?> deleteArticle(@PathVariable String articleId) {
        GenericResponse genericResponse;
        try {
            Article article = Article.builder()
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .articleId(articleId)
                    .build();
            Article article1 = articleService.deleteArticleByIdAndCurrentUser(article);
            genericResponse =
                    GenericResponse.builder()
                            .status("200")
                            .message("success")
                            .message("delete successfully")
//                            .payload(article1)
                            .build();

            return ResponseEntity.ok(genericResponse);

        } catch (Exception ex) {
            genericResponse =
                    GenericResponse
                            .builder()
                            .message("delete failed")
                            .status("500")
                            .message(ex.getMessage())
                            .build();
            ex.printStackTrace();
            return ResponseEntity.ok(genericResponse);
        }

    }
}
