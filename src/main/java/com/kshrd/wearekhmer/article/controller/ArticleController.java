package com.kshrd.wearekhmer.article.controller;

import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.article.model.request.ArticleRequest;
import com.kshrd.wearekhmer.article.model.request.ArticleUpdateRequest;
import com.kshrd.wearekhmer.article.response.ArticleResponse;
import com.kshrd.wearekhmer.article.service.ArticleService;
import com.kshrd.wearekhmer.exception.CustomRuntimeException;
import com.kshrd.wearekhmer.files.config.FileConfig;
import com.kshrd.wearekhmer.files.controller.FileController;
import com.kshrd.wearekhmer.files.service.IFileService;
import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import com.kshrd.wearekhmer.utils.serviceClassHelper.ServiceClassHelper;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import com.sun.research.ws.wadl.HTTPMethods;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.sql.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/article")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class ArticleController {
  private ArticleService articleService;
  private WeAreKhmerCurrentUser weAreKhmerCurrentUser;

  private final IFileService IFileService;

  private final FileConfig fileConfig;
  private static final Integer PAGE_SIZE = 10;

  private final WeAreKhmerValidation weAreKhmerValidation;

  private final ServiceClassHelper serviceClassHelper;

  private final IFileService fileService;

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

  private Integer getNextPageForCurrentUser(Integer page) {
    int numberOfRecord = serviceClassHelper.getTotalOfRecordInArticleTbForCurrentUser();
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
  public ResponseEntity<?> getAllArticles(
    @RequestParam(value = "page", required = false) Integer page
  ) {
    GenericResponse genericResponse;

    try {
      if (page != null) {
        Integer nextPage = getNextPage(page);
        List<ArticleResponse> articleResponseList = articleService.getAllArticlesWithPaginate(
          PAGE_SIZE,
          nextPage
        );
        return ResponseEntity
          .ok()
          .body(
            GenericResponse
              .builder()
              .title("success")
              .payload(articleResponseList)
              .message("Get data successfully.")
              .status("200")
              .build()
          );
      }
      List<ArticleResponse> articleResponses = articleService.getAllArticles();
      genericResponse =
        GenericResponse
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

  @Operation(summary = "(Get all articles for current author)")
  @GetMapping("/user")
  public ResponseEntity<?> getAllArticlesForCurrentUser(
    @RequestParam(value = "page", required = false) Integer page
  ) {
    GenericResponse genericResponse;
    try {
      if (page != null) {
        Integer nextPage = getNextPageForCurrentUser(page);
        List<ArticleResponse> articleResponseList = articleService.getArticlesForCurrentUserWithPaginate(
          weAreKhmerCurrentUser.getUserId(),
          PAGE_SIZE,
          nextPage
        );
        return ResponseEntity
          .ok()
          .body(
            GenericResponse
              .builder()
              .status("200")
              .message("Get data successfully.")
              .payload(articleResponseList)
              .title("error.")
              .build()
          );
      }
      String currentUerId = weAreKhmerCurrentUser.getUserId();
      List<ArticleResponse> articles = articleService.getArticlesForCurrentUser(
        currentUerId
      );
      genericResponse =
        GenericResponse
          .builder()
          .status("200")
          .message("request successfully")
          .payload(articles)
          .title("success")
          .build();
      return ResponseEntity.ok(genericResponse);
    } catch (Exception ex) {
      genericResponse =
        GenericResponse
          .builder()
          .title("failed")
          .message(ex.getMessage())
          .status("500")
          .build();

      ex.printStackTrace();
      return ResponseEntity.internalServerError().body(genericResponse);
    }
  }

  @PostMapping("/user")
  @Operation(summary = "(Insert article for current author")
  public ResponseEntity<?> insertArticle(
    @RequestBody @Validated ArticleRequest articleRequest
  ) {
    GenericResponse genericResponse;

    //            processing image and article
    //            String imageName = fileService.uploadFile(multipartFile);
    //            save image to desired location
    //            Generate the image URL
    //            String generateImageUrl;
    //            generateImageUrl = "http://localhost:8080/api/v1/files/file/filename?name=" + imageName;
    //            Save image url to article object

    Article article = Article
      .builder()
      .title(articleRequest.getTitle())
      .subTitle(articleRequest.getSubTitle())
      .description(articleRequest.getDescription())
      .categoryId(articleRequest.getCategoryId())
      .userId(weAreKhmerCurrentUser.getUserId())
      .build();

    weAreKhmerValidation.validateCategoryId(article.getCategoryId());
    try {
      Article article1 = articleService.insertArticle(article);
      genericResponse =
        GenericResponse
          .builder()
          .title("success")
          .status("200")
          .message("insert successfully successfully")
          .payload(article1)
          .build();
      return ResponseEntity.ok(genericResponse);
    } catch (Exception ex) {
      genericResponse =
        GenericResponse
          .builder()
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
    weAreKhmerValidation.validateArticleId(articleId);
    try {
      ArticleResponse article = articleService.getArticleById(articleId);
      genericResponse =
        GenericResponse
          .builder()
          .status("200")
          .title("success")
          .message("request successfully")
          .payload(article)
          .build();
      return ResponseEntity.ok(genericResponse);
    } catch (Exception ex) {
      genericResponse =
        GenericResponse
          .builder()
          .title("request failed")
          .status("500")
          .message(ex.getMessage())
          .build();
      ex.printStackTrace();
      return ResponseEntity.internalServerError().body(genericResponse);
    }
  }

  @GetMapping("/user/{articleId}")
  @Operation(summary = "(Get article by id for current author)")
  public ResponseEntity<?> getArticleByIdForCurrentUser(
    @PathVariable String articleId
  ) {
    weAreKhmerValidation.validateArticleIdByCurrentUser(
      articleId,
      weAreKhmerCurrentUser.getUserId()
    );
    try {
      ArticleResponse articleResponse = articleService.getArticleByIdForCurrentUser(
        articleId,
        weAreKhmerCurrentUser.getUserId()
      );

      return ResponseEntity
        .ok()
        .body(
          GenericResponse
            .builder()
            .title("success")
            .status("200")
            .payload(articleResponse)
            .message("Get article successfully.")
            .build()
        );
    } catch (Exception ex) {
      ex.printStackTrace();
      return ResponseEntity
        .internalServerError()
        .body(
          GenericResponse
            .builder()
            .title("error")
            .status("500")
            .message(ex.getMessage())
            .build()
        );
    }
  }

  @PutMapping("/user")
  @Operation(summary = "(Update article by id for current author)")
  public ResponseEntity<?> updateArticle(
    @RequestBody @Validated ArticleUpdateRequest article
  ) {
    GenericResponse genericResponse;

    weAreKhmerValidation.validateArticleIdByCurrentUser(
      article.getArticleId(),
      weAreKhmerCurrentUser.getUserId()
    );
    weAreKhmerValidation.validateCategoryId(article.getCategoryId());
    try {
      Article article1 = Article
        .builder()
        .articleId(article.getArticleId())
        .title(article.getTitle())
        .subTitle(article.getSubTitle())
        .description(article.getDescription())
        .userId(weAreKhmerCurrentUser.getUserId())
        .categoryId(article.getCategoryId())
        .build();

      Article article2 = articleService.updateArticle(article1);
      genericResponse =
        GenericResponse
          .builder()
          .status("200")
          .title("success")
          .message("update successfully.")
          .payload(article2)
          .build();
      return ResponseEntity.ok(genericResponse);
    } catch (Exception ex) {
      genericResponse =
        GenericResponse
          .builder()
          .title("failed")
          .message(ex.getMessage())
          .status("500")
          .build();
      ex.printStackTrace();
      return ResponseEntity.internalServerError().body(genericResponse);
    }
  }

  @DeleteMapping("/user/{articleId}")
  @Operation(summary = "(Delete article by id for current author)")
  public ResponseEntity<?> deleteArticle(@PathVariable String articleId) {
    GenericResponse genericResponse;
    weAreKhmerValidation.validateArticleIdByCurrentUser(
      articleId,
      weAreKhmerCurrentUser.getUserId()
    );
    try {
      Article article = Article
        .builder()
        .userId(weAreKhmerCurrentUser.getUserId())
        .articleId(articleId)
        .build();

      Article article1 = articleService.deleteArticleByIdAndCurrentUser(
        article
      );
      if (article1 == null) {
        throw new CustomRuntimeException(
          "You are not you own of this article or this article is not exist."
        );
      }

      //            delete image file if article exist
      fileService.deleteFileByFileName(article1.getImage());

      genericResponse =
        GenericResponse
          .builder()
          .status("200")
          .message("success")
          .message("delete successfully")
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

  @Operation(summary = "(Get articles by category name)")
  @GetMapping("/category/{categoryName}")
  public ResponseEntity<?> getAllArticleByCategoryName(
    @PathVariable String categoryName,
    @RequestParam(defaultValue = "1", required = false) Integer page
  ) {

    weAreKhmerValidation.checkCategoryNameExist(categoryName);
    try {
      Integer nextPage = getNextPage(page);
      List<ArticleResponse> articleResponseList = articleService.getAllArticleByCategoryName(
        categoryName,
        PAGE_SIZE,
        nextPage
      );

      return ResponseEntity.ok(
        GenericResponse
          .builder()
          .message("Get data successfully.")
          .status("200")
          .title("success")
          .payload(articleResponseList)
          .build()
      );
    } catch (Exception ex) {
      ex.printStackTrace();
      return ResponseEntity
        .internalServerError()
        .body(
          GenericResponse
            .builder()
            .message(ex.getMessage())
            .status("500")
            .title("error")
            .build()
        );
    }
  }

  @GetMapping("/most-view")
  @Operation(summary = "(Get articles by most view - only 20 rows was returned)")
  public ResponseEntity<?> getArticleByMostViewLimit20() {
    try {
      List<ArticleResponse> articleResponseList = articleService.getArticleByMostViewLimit20();
      return ResponseEntity
        .ok()
        .body(
          GenericResponse
            .builder()
            .title("success")
            .message("Fetching data successfully.")
            .payload(articleResponseList)
            .status("200")
            .build()
        );
    } catch (Exception ex) {
      ex.printStackTrace();
      return ResponseEntity
        .internalServerError()
        .body(
          GenericResponse
            .builder()
            .status("500")
            .message(ex.getMessage())
            .title("error.")
            .build()
        );
    }
  }

  @PostMapping("/increase/{articleId}")
  @Operation(summary = "(Increase article view count.")
  public ResponseEntity<?> increaseArticleViewCount(
    @PathVariable String articleId
  ) {
    weAreKhmerValidation.validateArticleId(articleId);
    try {
      String returnArticleId = articleService.increaseArticleViewCount(
        articleId
      );
      if (returnArticleId == null || returnArticleId.isEmpty()) {
        throw new CustomRuntimeException(
          "This article id " + articleId + " is not is not exist"
        );
      }
      return ResponseEntity
        .ok()
        .body(
          GenericResponse
            .builder()
            .title("success")
            .message("Article insert successfully.")
            .payload(returnArticleId)
            .build()
        );
    } catch (Exception ex) {
      ex.printStackTrace();
      return ResponseEntity
        .internalServerError()
        .body(
          GenericResponse
            .builder()
            .status("500")
            .message("Internal server error.")
            .title("error.")
            .build()
        );
    }
  }

  @GetMapping("/last-24h")
  @Operation(summary = "Get articles by last 24 hours")
  public ResponseEntity<?> getAllArticlesByYesterday() {
    GenericResponse genericResponse;
    try {
      List<ArticleResponse> articleResponseList = articleService.getAllArticlesByYesterday();
      genericResponse =
        GenericResponse
          .builder()
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
  @Operation(summary = "Get articles by last week")
  public ResponseEntity<?> getAllArticlesByLastWeek() {
    GenericResponse genericResponse;
    try {
      List<ArticleResponse> articleResponseList = articleService.getAllArticlesByLastWeek();
      genericResponse =
        GenericResponse
          .builder()
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
  @Operation(summary = "Get articles by last month")
  public ResponseEntity<?> getAllArticlesByLastMonth() {
    GenericResponse genericResponse;
    try {
      List<ArticleResponse> articleResponseList = articleService.getAllArticlesByLastMonth();
      genericResponse =
        GenericResponse
          .builder()
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
  @Operation(summary = "Get articles by last year")
  public ResponseEntity<?> getAllArticlesByLastYear() {
    GenericResponse genericResponse;
    try {
      List<ArticleResponse> articleResponseList = articleService.getAllArticlesByLastYear();
      genericResponse =
        GenericResponse
          .builder()
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
  public ResponseEntity<?> getAllArticlesByDateRange(
    Date startDate,
    Date endDate
  ) {
    GenericResponse genericResponse;
    try {
      List<ArticleResponse> articleResponseList = articleService.getAllArticlesByDateRange(
        startDate,
        endDate
      );
      genericResponse =
        GenericResponse
          .builder()
          .status("200")
          .payload(articleResponseList)
          .title("success")
          .message(
            "You have successfully got articles recorded between " +
            startDate +
            " and " +
            endDate
          )
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

  @Operation(summary = "(Get articles by category id)")
  @GetMapping("/category/categoryId")
  public ResponseEntity<?> getAllArticleByCategoryId(
           String categoryId,
           @RequestParam(defaultValue = "1", required = false) Integer page
  ) {
    weAreKhmerValidation.validateCategoryId(categoryId);
    Integer nextPage = getNextPage(page);
      List<ArticleResponse> articleResponseList = articleService.getAllArticleByCategoryId(
              categoryId,
              PAGE_SIZE,
              nextPage
      );
      GenericResponse genericResponse = GenericResponse.builder()
              .status("200")
              .title("success")
              .payload(articleResponseList)
              .message("You have successfully get articles in this category")
              .build();
      return ResponseEntity.ok(genericResponse);
  }

  @Operation(summary = "(Get most-view articles for current author)")
  @GetMapping("/user/most-view")
  public ResponseEntity<?> getAllArticleCurrentUserByMostView(
          @RequestParam(defaultValue = "1", required = false) Integer page
  ) {

    weAreKhmerValidation.checkAuthorExist(weAreKhmerCurrentUser.getUserId());

    Integer nextPage = getNextPage(page);

    List<ArticleResponse> articleResponseList = articleService.getAllArticleCurrentUserByMostView(
            weAreKhmerCurrentUser.getUserId(),
            PAGE_SIZE,
            nextPage
    );
    GenericResponse genericResponse = GenericResponse.builder()
            .status("200")
            .title("success")
            .payload(articleResponseList)
            .message("You have successfully get all most view articles.")
            .build();
    return ResponseEntity.ok(genericResponse);
  }
  @Operation(summary = "(Get latest article for current author)")
  @GetMapping("/user/latest")
  public ResponseEntity<?> getAllArticleCurrentUserByLatest(
          @RequestParam(defaultValue = "1", required = false) Integer page
  ) {

    weAreKhmerValidation.checkAuthorExist(weAreKhmerCurrentUser.getUserId());

    Integer nextPage = getNextPage(page);

    List<ArticleResponse> articleResponseList = articleService.getAllArticleCurrentUserByLatest(
            weAreKhmerCurrentUser.getUserId(),
            PAGE_SIZE,
            nextPage
    );
    GenericResponse genericResponse = GenericResponse.builder()
            .status("200")
            .title("success")
            .payload(articleResponseList)
            .message("You have successfully get all latest articles.")
            .build();
    return ResponseEntity.ok(genericResponse);
  }

  @Operation(summary = "(Get yesterday articles for current author)")
  @GetMapping("/user/yesterday")
  public ResponseEntity<?> getAllArticleCurrentUserByYesterday(
          @RequestParam(defaultValue = "1", required = false) Integer page
  ) {

    weAreKhmerValidation.checkAuthorExist(weAreKhmerCurrentUser.getUserId());

    Integer nextPage = getNextPage(page);

    List<ArticleResponse> articleResponseList = articleService.getAllArticleCurrentUserByYesterday(
            weAreKhmerCurrentUser.getUserId(),
            PAGE_SIZE,
            nextPage
    );
    GenericResponse genericResponse = GenericResponse.builder()
            .status("200")
            .title("success")
            .payload(articleResponseList)
            .message("You have successfully get all articles by yesterday.")
            .build();
    return ResponseEntity.ok(genericResponse);
  }

  @Operation(summary = "(Get articles per week for current author)")
  @GetMapping("/user/per-week")
  public ResponseEntity<?> getAllArticleCurrentUserPerWeek(
          @RequestParam(defaultValue = "1", required = false) Integer page
  ) {

    weAreKhmerValidation.checkAuthorExist(weAreKhmerCurrentUser.getUserId());

    Integer nextPage = getNextPage(page);

    List<ArticleResponse> articleResponseList = articleService.getAllArticleCurrentUserPerWeek(
            weAreKhmerCurrentUser.getUserId(),
            PAGE_SIZE,
            nextPage
    );
    GenericResponse genericResponse = GenericResponse.builder()
            .status("200")
            .title("success")
            .payload(articleResponseList)
            .message("You have successfully get all articles per week.")
            .build();
    return ResponseEntity.ok(genericResponse);
  }


  @Operation(summary = "(Get articles per month for current author )")
  @GetMapping("/user/per-month")
  public ResponseEntity<?> getAllArticleCurrentUserPerMonth(
          @RequestParam(defaultValue = "1", required = false) Integer page
  ) {

    Integer nextPage = getNextPage(page);

    List<ArticleResponse> articleResponseList = articleService.getAllArticleCurrentUserPerMonth(
            weAreKhmerCurrentUser.getUserId(),
            PAGE_SIZE,
            nextPage
    );
    GenericResponse genericResponse = GenericResponse.builder()
            .status("200")
            .title("success")
            .payload(articleResponseList)
            .message("You have successfully get all articles per month.")
            .build();
    return ResponseEntity.ok(genericResponse);
  }


  @Operation(summary = "(Get articles per year for current author )")
  @GetMapping("/user/per-year")
  public ResponseEntity<?> getAllArticleCurrentUserPerYear(
          @RequestParam(defaultValue = "1", required = false) Integer page
  ) {

    Integer nextPage = getNextPage(page);

    List<ArticleResponse> articleResponseList = articleService.getAllArticleCurrentUserPerYear(
            weAreKhmerCurrentUser.getUserId(),
            PAGE_SIZE,
            nextPage
    );
    GenericResponse genericResponse = GenericResponse.builder()
            .status("200")
            .title("success")
            .payload(articleResponseList)
            .message("You have successfully get all articles per year.")
            .build();
    return ResponseEntity.ok(genericResponse);
  }
}
