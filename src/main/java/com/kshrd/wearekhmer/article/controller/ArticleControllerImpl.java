package com.kshrd.wearekhmer.article.controller;


import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.article.model.request.ArticleRequest;
import com.kshrd.wearekhmer.article.service.IArticleService;
import com.kshrd.wearekhmer.files.config.FileConfig;
import com.kshrd.wearekhmer.files.service.IFileService;
import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
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
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
    public Article getArticleById(String articleId) {
        GenericResponse genericResponse;
        try {
            GenericResponse.builder()
                    .build();
            return articleService.getArticleById(articleId);

        } catch (Exception ex) {
            return null;
        }
    }
}
