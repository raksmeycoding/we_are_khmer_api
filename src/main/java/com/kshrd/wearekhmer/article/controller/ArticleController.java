package com.kshrd.wearekhmer.article.controller;


import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.article.model.request.ArticleRequest;
import com.kshrd.wearekhmer.article.model.request.ArticleUpdateRequest;
import com.kshrd.wearekhmer.article.response.ArticleResponse;
import com.kshrd.wearekhmer.article.service.ArticleService;
import com.kshrd.wearekhmer.exception.CustomRuntimeException;
import com.kshrd.wearekhmer.files.config.FileConfig;
import com.kshrd.wearekhmer.files.service.IFileService;
import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import com.kshrd.wearekhmer.utils.serviceClassHelper.ServiceClassHelper;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class ArticleController {


    private ArticleService articleService;
    private WeAreKhmerCurrentUser weAreKhmerCurrentUser;

    private final IFileService fileService;

    private final FileConfig fileConfig;
    private static final Integer PAGE_SIZE = 10;

    private final WeAreKhmerValidation weAreKhmerValidation;

    private final ServiceClassHelper serviceClassHelper;


    private Integer getNextPage(Integer page) {
        int numberOfRecord = serviceClassHelper.getTotalOfRecordInArticleTb();
        System.out.println(numberOfRecord);
        int totalPage = (int) Math.ceil((double) numberOfRecord / PAGE_SIZE);
        System.out.println(totalPage);
        if (page > totalPage) {
            page = totalPage;
        }
        weAreKhmerValidation.validatePageNumber(page);
        return (page - 1) * PAGE_SIZE;
    }

    @GetMapping
    @Operation(summary = "(Get all articles)")
    public ResponseEntity<?> getAllArticles(@RequestParam(value = "page", required = false) Integer page) {
        GenericResponse genericResponse;

        try {
            if (page != null) {
                Integer nextPage = getNextPage(page);
                List<ArticleResponse> articleResponseList = articleService.getAllArticlesWithPaginate(PAGE_SIZE, nextPage);
                return ResponseEntity.ok().body(GenericResponse.builder()
                        .title("success")
                        .payload(articleResponseList)
                        .message("Get data successfully.")
                        .status("200")
                        .build());
            }
            List<ArticleResponse> articleResponses =
                    articleService.getAllArticles();
            genericResponse = GenericResponse
                    .builder()
                    .status("200")
                    .title("success")
                    .payload(articleResponses)
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


    @Operation(summary = "(Get all articles current for current user)")
    @GetMapping("/user")
    public ResponseEntity<?> getAllArticlesForCurrentUser(@RequestParam(value = "page", required = false) Integer page) {
        GenericResponse genericResponse;
        try {

            if (page != null) {
                Integer nextPage = getNextPage(page);
                List<ArticleResponse> articleResponseList = articleService.getArticlesForCurrentUserWithPaginate(weAreKhmerCurrentUser.getUserId(), PAGE_SIZE, nextPage);
                return ResponseEntity.ok().body(GenericResponse.builder()
                        .status("200")
                        .message("Get data successfully.")
                        .payload(articleResponseList)
                        .title("error.")
                        .build());
            }
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


    @PostMapping("/user")
    @Operation(summary = "(Insert article for current user")
    public ResponseEntity<?> insertArticle(@RequestBody @Validated ArticleRequest articleRequest) {
        GenericResponse genericResponse;
        try {

//            processing image and article
//            String imageName = fileService.uploadFile(multipartFile);
//            save image to desired location
//            Generate the image URL
//            String generateImageUrl;
//            generateImageUrl = "http://localhost:8080/api/v1/files/file/filename?name=" + imageName;
//            Save image url to article object

            Article article = Article.builder()
                    .title(articleRequest.getTitle())
                    .subTitle(articleRequest.getSubTitle())
                    .description(articleRequest.getDescription())
                    .categoryId(articleRequest.getCategoryId())
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .build();


            Article article1 = articleService.insertArticle(article);


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


    @GetMapping("/{articleId}")
    @Operation(summary = "(Get article by id)")
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


    @GetMapping("/user/{articleId}")
    @Operation(summary = "(Get article by id for current user)")
    public ResponseEntity<?> getArticleByIdForCurrentUser(@PathVariable String articleId) {
        try {

            ArticleResponse articleResponse = articleService.getArticleByIdForCurrentUser(articleId, weAreKhmerCurrentUser.getUserId());
            if (articleResponse == null) {
                throw new CustomRuntimeException("This article is not exist or not own article.");
            }
            return ResponseEntity.ok().body(GenericResponse.builder()
                    .title("success")
                    .status("200")
                    .payload(articleResponse)
                    .message("Get article successfully.")
                    .build());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(GenericResponse.builder()
                    .title("error")
                    .status("500")
                    .message(ex.getMessage())
                    .build());
        }
    }


    @PutMapping("/user")
    @Operation(summary = "(Update article by id for current user)")
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


    @DeleteMapping("/user/{articleId}")
    @Operation(summary = "(Delete article by id for current user)")
    public ResponseEntity<?> deleteArticle(@PathVariable String articleId) {
        GenericResponse genericResponse;
        try {
            Article article = Article.builder()
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .articleId(articleId)
                    .build();
            Article article1 = articleService.deleteArticleByIdAndCurrentUser(article);
            if (article1 == null) {
                throw new CustomRuntimeException("You are not you own of this article or this article is not exist.");
            }
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


    @Operation(summary = "(Get Articles by category name)")
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<?> getAllArticleByCategoryName(@PathVariable String categoryName, @RequestParam(defaultValue = "1", required = false) Integer page) {
        try {
            Integer nextPage = getNextPage(page);
            List<ArticleResponse> articleResponseList = articleService.getAllArticleByCategoryName(categoryName, PAGE_SIZE, nextPage);
            if (articleResponseList.isEmpty()) {
                throw new CustomRuntimeException("This article by this category is not exist");
            }
            return ResponseEntity.ok(GenericResponse.builder()
                    .message("Get data successfully.")
                    .status("200")
                    .title("success")
                    .payload(articleResponseList)
                    .build());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(GenericResponse.builder()
                    .message(ex.getMessage())
                    .status("500")
                    .title("error")
                    .build());
        }
    }


    @GetMapping("/most-view")
    @Operation(summary = "(Get article by most view - only 20 rows was returned)")
    public ResponseEntity<?> getArticleByMostViewLimit20() {
        try {
            List<ArticleResponse> articleResponseList = articleService.getArticleByMostViewLimit20();
            return ResponseEntity.ok().body(GenericResponse.builder()
                    .title("success")
                    .message("Fetching data successfully.")
                    .payload(articleResponseList)
                    .status("200")
                    .build());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(GenericResponse.builder()
                    .status("500")
                    .message(ex.getMessage())
                    .title("error.")
                    .build());
        }
    }


    @PostMapping("/increase/{articleId}")
    @Operation(summary = "(Increase article view count.")
    public ResponseEntity<?> increaseArticleViewCount(@PathVariable String articleId) {
        try {
            String returnArticleId = articleService.increaseArticleViewCount(articleId);
            if (returnArticleId == null || returnArticleId.isEmpty()) {
                throw new CustomRuntimeException("This article id " + articleId + " is not is not exist");
            }
            return ResponseEntity.ok().body(GenericResponse.builder()
                    .title("success")
                    .message("Article insert successfully.")
                    .payload(returnArticleId)
                    .build());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(GenericResponse.builder()
                    .status("500")
                    .message("Internal server error.")
                    .title("error.")
                    .build());
        }
    }

    @GetMapping("/last-24h")
    @Operation(summary = "Get article by last 24 hours")
    public ResponseEntity<?> getAllArticlesByYesterday(){
        GenericResponse genericResponse;
        try {

            List<ArticleResponse> articleResponseList = articleService.getAllArticlesByYesterday();
            genericResponse = GenericResponse.builder()
                    .status("200")
                    .payload(articleResponseList)
                    .title("success")
                    .message("You have successfully got last 24 hour articles recorded")
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
    @GetMapping("/last-week")
    @Operation(summary = "Get article by last week")
    public ResponseEntity<?> getAllArticlesByLastWeek(){
        GenericResponse genericResponse;
        try {

            List<ArticleResponse> articleResponseList = articleService.getAllArticlesByLastWeek();
            genericResponse = GenericResponse.builder()
                    .status("200")
                    .payload(articleResponseList)
                    .title("success")
                    .message("You have successfully got last week articles recorded")
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

    @GetMapping("/last-month")
    @Operation(summary = "Get article by last month")
    public ResponseEntity<?> getAllArticlesByLastMonth(){
        GenericResponse genericResponse;
        try {

            List<ArticleResponse> articleResponseList = articleService.getAllArticlesByLastMonth();
            genericResponse = GenericResponse.builder()
                    .status("200")
                    .payload(articleResponseList)
                    .title("success")
                    .message("You have successfully got last month articles recorded")
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

    @GetMapping("/last-year")
    @Operation(summary = "Get article by last year")
    public ResponseEntity<?> getAllArticlesByLastYear(){
        GenericResponse genericResponse;
        try {

            List<ArticleResponse> articleResponseList = articleService.getAllArticlesByLastYear();
            genericResponse = GenericResponse.builder()
                    .status("200")
                    .payload(articleResponseList)
                    .title("success")
                    .message("You have successfully got last year articles recorded")
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

    @GetMapping("/date-range")
    @Operation(summary = "Get article by date range")
    public ResponseEntity<?> getAllArticlesByDateRange(Date startDate, Date endDate){
        GenericResponse genericResponse;
        try {

            List<ArticleResponse> articleResponseList = articleService.getAllArticlesByDateRange(startDate,endDate);
            genericResponse = GenericResponse.builder()
                    .status("200")
                    .payload(articleResponseList)
                    .title("success")
                    .message("You have successfully got articles recorded between " + startDate + " and "+endDate)
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
}
