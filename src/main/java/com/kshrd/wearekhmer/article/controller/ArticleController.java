package com.kshrd.wearekhmer.article.controller;

import com.kshrd.wearekhmer.article.model.Response.ArticleResponse2;
import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.article.model.request.ArticleRequest;
import com.kshrd.wearekhmer.article.model.request.ArticleUpdateRequest;
import com.kshrd.wearekhmer.article.repository.ArticleMapper;
import com.kshrd.wearekhmer.article.repository.FilterArticleCriteria;
import com.kshrd.wearekhmer.article.response.ArticleResponse;
import com.kshrd.wearekhmer.article.service.ArticleService;
import com.kshrd.wearekhmer.exception.CustomRuntimeException;
import com.kshrd.wearekhmer.exception.ValidateException;
import com.kshrd.wearekhmer.files.config.FileConfig;
import com.kshrd.wearekhmer.files.service.IFileService;
import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import com.kshrd.wearekhmer.utils.serviceClassHelper.ServiceClassHelper;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    private final ArticleMapper articleMapper;

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


    @GetMapping("/filter")
    @Operation(summary = "(Filter all articles)")
    public ResponseEntity<?> filterArticles(@RequestParam(required = false) String title,
                                            @RequestParam(required = false) String publishDate,
                                            @RequestParam(required = false) String categoryId,
                                            @RequestParam(required = false) String startDate,
                                            @RequestParam(required = false) String endDate,
                                            @RequestParam(value = "view", required = false) String view,
                                            @RequestParam(value = "day", required = false) String day,
                                            @RequestParam(value = "userId", required = false) String userId,
                                            @RequestParam(value = "page", required = false) Integer page) {
        try {

            int totalRecords = articleMapper.getTotalRecordOfArticleTb();
            if (page != null) {
                int totalPages = (int) Math.ceil((double) totalRecords / 10);
                if (page > totalPages)
                    page = totalPages;
            }

            Map<String, Object> param = new HashMap<>();
            param.put("title", title);
//        List<ArticleResponse2> filteredArticles = articleMapper.filterArticles(title, date, categoryId );

//        List<ArticleResponse2> filteredArticles = articleMapper.getArticlesByFilter(title, date, categoryId);
            FilterArticleCriteria filterArticleCriteria = new FilterArticleCriteria();
            filterArticleCriteria.setTitle(title);
            filterArticleCriteria.setPublishDate(publishDate);
            filterArticleCriteria.setCategoryId(categoryId);
            filterArticleCriteria.setStartDate(startDate);
            filterArticleCriteria.setEndDate(endDate);
            filterArticleCriteria.setView(view);
            filterArticleCriteria.setDay(day);
            filterArticleCriteria.setUserId(userId);
            filterArticleCriteria.setPage(page);
            List<ArticleResponse2> filteredArticles = articleMapper.getArticlesByFilter2(filterArticleCriteria);

            // Return the response using ResponseEntity
            return ResponseEntity.ok().body(GenericResponse.builder()
                    .message("Get data successfully.")
                    .title("success")
                    .statusCode(200)
                    .totalRecords(totalRecords)
                    .payload(filteredArticles)
                    .build());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException(ex.getCause());
        }
    }

    @GetMapping
    @Operation(summary = "(Get all articles)")
    public ResponseEntity<?> getAllArticles(
            @RequestParam(defaultValue = "1", required = false) Integer page,
            @RequestParam(value = "userId", required = false) String userId

    ) {
        GenericResponse genericResponse;


        Integer totalRecords = articleMapper.totalArticles();


        try {
            Integer nextPage = getNextPage(page);
            List<ArticleResponse2> articleResponses = articleService.getAllArticlesByLatest(
                    PAGE_SIZE,
                    nextPage,
                    userId
            );
            genericResponse =
                    GenericResponse
                            .builder()
                            .totalRecords(totalRecords)
                            .status("200")
                            .title("success")
                            .message("You have successfully get all articles")
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

//    @Operation(summary = "(Get all articles for current author)")
//    @GetMapping("/author")
//    @Hidden
//    public ResponseEntity<?> getAllArticlesForCurrentUser(
//            @RequestParam(value = "page", required = false) Integer page
//    ) {
//        GenericResponse genericResponse;
//        try {
//            if (page != null) {
//                Integer nextPage = getNextPageForCurrentUser(page);
//                List<ArticleResponse> articleResponseList = articleService.getArticlesForCurrentUserWithPaginate(
//                        weAreKhmerCurrentUser.getUserId(),
//                        PAGE_SIZE,
//                        nextPage
//                );
//                return ResponseEntity
//                        .ok()
//                        .body(
//                                GenericResponse
//                                        .builder()
//                                        .status("200")
//                                        .message("Get data successfully.")
//                                        .payload(articleResponseList)
//                                        .title("success")
//                                        .build()
//                        );
//            }
//            String currentUerId = weAreKhmerCurrentUser.getUserId();
//            List<ArticleResponse> articles = articleService.getArticlesForCurrentUser(
//                    currentUerId
//            );
//            genericResponse =
//                    GenericResponse
//                            .builder()
//                            .status("200")
//                            .message("request successfully")
//                            .payload(articles)
//                            .title("success")
//                            .build();
//            return ResponseEntity.ok(genericResponse);
//        } catch (Exception ex) {
//            genericResponse =
//                    GenericResponse
//                            .builder()
//                            .title("failed")
//                            .message(ex.getMessage())
//                            .status("500")
//                            .build();
//
//            ex.printStackTrace();
//            return ResponseEntity.internalServerError().body(genericResponse);
//        }
//    }

    @PostMapping("/author")
    @Operation(summary = "(Insert article for current author)")
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
                .image(articleRequest.getArticleImage())
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
    public ResponseEntity<?> getArticleById(@PathVariable String articleId,
                                            @RequestParam(value = "userId", required = false) String userId) {
        GenericResponse genericResponse;
        weAreKhmerValidation.validateArticleId(articleId);
        try {
            ArticleResponse2 article = articleService.getArticleById(articleId, userId);
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

    @GetMapping("/author/{articleId}")
    @Operation(summary = "(Get article by id for current author)")
    public ResponseEntity<?> getArticleByIdForCurrentUser(
            @PathVariable String articleId
    ) {
        weAreKhmerValidation.validateArticleIdByCurrentUser(
                articleId,
                weAreKhmerCurrentUser.getUserId()
        );
        try {
            ArticleResponse2 articleResponse = articleService.getArticleByIdForCurrentUser(
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

    @PutMapping("/author")
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
                    .image(article.getArticleImage())
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

    @DeleteMapping("/author/{articleId}")
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
    @GetMapping("/category/")
    @Hidden
    public ResponseEntity<?> getAllArticleByCategoryName(
            @PathVariable(value = "categoryName") String categoryName,
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
    @Operation(summary = "(Filters articles by most view ( limit 20 row ))")
    @Hidden
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
    @Operation(summary = "(Increase article view count)")
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

    @GetMapping("/yesterday")
    @Operation(summary = "Filter articles by yesterday")
    @Hidden
    public ResponseEntity<?> getAllArticlesByYesterday(@RequestParam(defaultValue = "1", required = false) Integer page) {
        GenericResponse genericResponse;
        try {
            Integer nextPage = getNextPage(page);
            List<ArticleResponse> articleResponseList = articleService.getAllArticlesByYesterday(
                    PAGE_SIZE,
                    nextPage
            );
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

    @GetMapping("/per-week")
    @Operation(summary = "Filter articles per week")
    @Hidden
    public ResponseEntity<?> getAllArticlesPerWeek(@RequestParam(defaultValue = "1", required = false) Integer page) {
        GenericResponse genericResponse;
        try {
            Integer nextPage = getNextPage(page);
            List<ArticleResponse> articleResponseList = articleService.getAllArticlesPerWeek(
                    PAGE_SIZE,
                    nextPage
            );
            genericResponse =
                    GenericResponse
                            .builder()
                            .status("200")
                            .payload(articleResponseList)
                            .title("success")
                            .message("You have successfully got per week articles recorded")
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

    @GetMapping("/per-month")
    @Operation(summary = "Filter articles per month")
    @Hidden
    public ResponseEntity<?> getAllArticlesPerMonth(@RequestParam(defaultValue = "1", required = false) Integer page) {
        GenericResponse genericResponse;
        try {
            Integer nextPage = getNextPage(page);
            List<ArticleResponse> articleResponseList = articleService.getAllArticlesPerMonth(
                    PAGE_SIZE,
                    nextPage
            );
            genericResponse =
                    GenericResponse
                            .builder()
                            .status("200")
                            .payload(articleResponseList)
                            .title("success")
                            .message("You have successfully got per month articles recorded")
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

    @GetMapping("/per-year")
    @Operation(summary = "Filter articles per year")
    @Hidden
    public ResponseEntity<?> getAllArticlesByPerYear(@RequestParam(defaultValue = "1", required = false) Integer page) {
        GenericResponse genericResponse;
        try {
            Integer nextPage = getNextPage(page);
            List<ArticleResponse> articleResponseList = articleService.getAllArticlesPerYear(
                    PAGE_SIZE,
                    nextPage
            );
            genericResponse =
                    GenericResponse
                            .builder()
                            .status("200")
                            .payload(articleResponseList)
                            .title("success")
                            .message("You have successfully got per year articles recorded")
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
    @Operation(summary = "Filter articles by date range")
    @Hidden
    public ResponseEntity<?> getAllArticlesByDateRange(
            Date startDate,
            Date endDate
    ) {
        GenericResponse genericResponse;
        if (startDate.compareTo(endDate) > 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " start date should not be greater than end date");
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
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getAllArticleByCategoryId(
            @PathVariable String categoryId,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(defaultValue = "1", required = false) Integer page
    ) {
        weAreKhmerValidation.validateCategoryId(categoryId);
        Integer nextPage = getNextPage(page);
        List<ArticleResponse2> articleResponseList = articleService.getAllArticleByCategoryId(
                categoryId,
                PAGE_SIZE,
                nextPage,
                userId
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
    @GetMapping("/author/most-view")
    public ResponseEntity<?> getAllArticleCurrentUserByMostView(
            @RequestParam(defaultValue = "1", required = false) Integer page
    ) {

        weAreKhmerValidation.checkAuthorExist(weAreKhmerCurrentUser.getUserId());

        Integer nextPage = getNextPage(page);

        List<ArticleResponse2> articleResponseList = articleService.getAllArticleCurrentUserByMostView(
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
    @GetMapping("/author")
    public ResponseEntity<?> getAllArticleCurrentUserByLatest(
            @RequestParam(defaultValue = "1", required = false) Integer page
    ) {
        GenericResponse genericResponse;
        weAreKhmerValidation.checkAuthorExist(weAreKhmerCurrentUser.getUserId());

        Integer nextPage = getNextPage(page);

        Integer totalRecordByLatest = articleMapper.getTotalRecordOfArticleForCurrentUser(weAreKhmerCurrentUser.getUserId());

        List<ArticleResponse2> articleResponseList = articleService.getAllArticleCurrentUserByLatest(
                weAreKhmerCurrentUser.getUserId(),
                PAGE_SIZE,
                nextPage
        );
        genericResponse = GenericResponse.builder()
                .totalRecords(totalRecordByLatest)
                .status("200")
                .title("success")
                .payload(articleResponseList)
                .message("You have successfully get all latest articles.")
                .build();
        return ResponseEntity.ok(genericResponse);


    }

    @Operation(summary = "(Get yesterday articles for current author)")
    @GetMapping("/author/yesterday")
    public ResponseEntity<?> getAllArticleCurrentUserByYesterday(
            @RequestParam(defaultValue = "1", required = false) Integer page
    ) {

        weAreKhmerValidation.checkAuthorExist(weAreKhmerCurrentUser.getUserId());

        Integer totalRecordByYesterday = articleMapper.totalArticleRecordByYesterdayForCurrentAuthor(weAreKhmerCurrentUser.getUserId());


        Integer nextPage = getNextPage(page);

        List<ArticleResponse2> articleResponseList = articleService.getAllArticleCurrentUserByYesterday(
                weAreKhmerCurrentUser.getUserId(),
                PAGE_SIZE,
                nextPage
        );
        GenericResponse genericResponse = GenericResponse.builder()
                .totalRecords(totalRecordByYesterday)
                .status("200")
                .title("success")
                .payload(articleResponseList)
                .message("You have successfully get all articles by yesterday.")
                .build();
        return ResponseEntity.ok(genericResponse);
    }

    @Operation(summary = "(Get articles per week for current author)")
    @GetMapping("/author/per-week")
    public ResponseEntity<?> getAllArticleCurrentUserPerWeek(
            @RequestParam(defaultValue = "1", required = false) Integer page
    ) {

        weAreKhmerValidation.checkAuthorExist(weAreKhmerCurrentUser.getUserId());

        Integer totalRecordPerWeek = articleMapper.totalArticleRecordByPerWeekForCurrentAuthor(weAreKhmerCurrentUser.getUserId());

        Integer nextPage = getNextPage(page);

        List<ArticleResponse2> articleResponseList = articleService.getAllArticleCurrentUserPerWeek(
                weAreKhmerCurrentUser.getUserId(),
                PAGE_SIZE,
                nextPage
        );
        GenericResponse genericResponse = GenericResponse.builder()
                .totalRecords(totalRecordPerWeek)
                .status("200")
                .title("success")
                .payload(articleResponseList)
                .message("You have successfully get all articles per week.")
                .build();
        return ResponseEntity.ok(genericResponse);
    }


    @Operation(summary = "(Get articles per month for current author )")
    @GetMapping("/author/per-month")
    public ResponseEntity<?> getAllArticleCurrentUserPerMonth(
            @RequestParam(defaultValue = "1", required = false) Integer page
    ) {

        Integer nextPage = getNextPage(page);
        Integer totalRecordByPerMonth = articleMapper.totalArticleRecordByPerMonthForCurrentAuthor(weAreKhmerCurrentUser.getUserId());

        List<ArticleResponse2> articleResponseList = articleService.getAllArticleCurrentUserPerMonth(
                weAreKhmerCurrentUser.getUserId(),
                PAGE_SIZE,
                nextPage
        );
        GenericResponse genericResponse = GenericResponse.builder()
                .totalRecords(totalRecordByPerMonth)
                .status("200")
                .title("success")
                .payload(articleResponseList)
                .message("You have successfully get all articles per month.")
                .build();
        return ResponseEntity.ok(genericResponse);


    }


    @Operation(summary = "(Get articles per year for current author )")
    @GetMapping("/author/per-year")
    public ResponseEntity<?> getAllArticleCurrentUserPerYear(
            @RequestParam(defaultValue = "1", required = false) Integer page
    ) {
        Integer totalRecordPerYear = articleMapper.totalArticleRecordByPerYearForCurrentAuthor(weAreKhmerCurrentUser.getUserId());
        Integer nextPage = getNextPage(page);


        List<ArticleResponse2> articles = articleService.getAllArticleCurrentUserPerYear(
                weAreKhmerCurrentUser.getUserId(),
                PAGE_SIZE,
                nextPage
        );
        GenericResponse genericResponse = GenericResponse.builder()
                .totalRecords(totalRecordPerYear)
                .status("200")
                .title("success")
                .payload(articles)
                .message("You have successfully get all articles per year.")
                .build();
        return ResponseEntity.ok(genericResponse);

    }


    @Operation(summary = "(Get total views per week for current author )")
    @GetMapping("/author/TotalViewPerWeek")
    @Hidden
    public ResponseEntity<?> getTotalViewCurrentAuthorPerWeek(
    ) {
        weAreKhmerValidation.checkAuthorExist(weAreKhmerCurrentUser.getUserId());

        Integer totalView = articleService.getTotalViewCurrentAuthorPerWeek(weAreKhmerCurrentUser.getUserId());

        GenericResponse genericResponse = GenericResponse.builder()
                .status("200")
                .title("success")
                .payload(totalView)
                .message("You have successfully get total views per week.")
                .build();
        return ResponseEntity.ok(genericResponse);
    }

    @Operation(summary = "(Get total views per month for current author )")
    @GetMapping("/author/TotalViewPerMonth")
    @Hidden
    public ResponseEntity<?> getTotalViewCurrentAuthorPerMonth(
    ) {
        weAreKhmerValidation.checkAuthorExist(weAreKhmerCurrentUser.getUserId());

        Integer totalView = articleService.getTotalViewCurrentAuthorPerMonth(weAreKhmerCurrentUser.getUserId());

        GenericResponse genericResponse = GenericResponse.builder()
                .status("200")
                .title("success")
                .payload(totalView)
                .message("You have successfully get total views per month.")
                .build();
        return ResponseEntity.ok(genericResponse);
    }

    @Operation(summary = "(Get total views per year for current author )")
    @GetMapping("/author/TotalViewPerYear")
    @Hidden
    public ResponseEntity<?> getTotalViewCurrentAuthorPerYear(
    ) {
        weAreKhmerValidation.checkAuthorExist(weAreKhmerCurrentUser.getUserId());

        Integer totalView = articleService.getTotalViewCurrentAuthorPerYear(weAreKhmerCurrentUser.getUserId());

        GenericResponse genericResponse = GenericResponse.builder()
                .status("200")
                .title("success")
                .payload(totalView)
                .message("You have successfully get total views per year.")
                .build();
        return ResponseEntity.ok(genericResponse);
    }

    @Operation(summary = "(Get total views per week for admin )")
    @GetMapping("/admin/TotalViewPerWeek")
    public ResponseEntity<?> getTotalViewAdminPerWeek(
    ) {
        Integer totalView = articleService.getTotalViewAdminPerWeek();
        GenericResponse genericResponse = GenericResponse.builder()
                .status("200")
                .title("success")
                .payload(totalView)
                .message("You have successfully get total views per week.")
                .build();
        return ResponseEntity.ok(genericResponse);
    }

    @Operation(summary = "(Get total views per month for admin )")
    @GetMapping("/admin/TotalViewPerMonth")
    public ResponseEntity<?> getTotalViewAdminPerMonth(

    ) {
        Integer totalView = articleService.getTotalViewAdminPerMonth();
        GenericResponse genericResponse = GenericResponse.builder()
                .status("200")
                .title("success")
                .payload(totalView)
                .message("You have successfully get total views per month.")
                .build();
        return ResponseEntity.ok(genericResponse);
    }

    @Operation(summary = "(Get total views per year for admin )")
    @GetMapping("/admin/TotalViewPerYear")
    public ResponseEntity<?> getTotalViewAdminPerYear(
    ) {
        Integer totalView = articleService.getTotalViewAdminPerYear();
        GenericResponse genericResponse = GenericResponse.builder()
                .status("200")
                .title("success")
                .payload(totalView)
                .message("You have successfully get total views per year.")
                .build();
        return ResponseEntity.ok(genericResponse);
    }


    @PostMapping("/adminBanArticle/{articleId}")
    @Operation(summary = "(Only admin has permission to ban this article.)")
    public ResponseEntity<?> adminBanArticle(@PathVariable String articleId) {
        boolean isArticleExist = articleMapper.isArticleExist(articleId);
        if(!isArticleExist) {
            throw new ValidateException("No article had been found.", HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
        }
        if(articleMapper.validateIsArticleAlreadyBand(articleId)) {
           throw new ValidateException("This article had been already banned.", HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.value());
        }
        boolean isban = articleMapper.adminBanArticle(articleId);
        System.out.println(isban);
        if (!isban) {
            throw new ValidateException("Article is not able to be ban.", HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }


        return ResponseEntity.ok().body(GenericResponse.builder()
                .statusCode(200)
                .title("success")
                .message("Article had been bad successfully.").build());
    }
}
