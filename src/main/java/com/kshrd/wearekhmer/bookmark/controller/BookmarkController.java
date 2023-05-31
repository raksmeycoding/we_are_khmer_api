package com.kshrd.wearekhmer.bookmark.controller;

import com.kshrd.wearekhmer.article.service.ArticleService;
import com.kshrd.wearekhmer.bookmark.model.entity.Bookmark;
import com.kshrd.wearekhmer.bookmark.model.reponse.BookmarkResponse;
import com.kshrd.wearekhmer.bookmark.model.request.BookmarkRequest;
import com.kshrd.wearekhmer.bookmark.service.IBookService;
import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookmark")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class BookmarkController {

    private final IBookService bookmarkService;
    private final ArticleService articleService;

    private WeAreKhmerCurrentUser weAreKhmerCurrentUser;

    private WeAreKhmerValidation weAreKhmerValidation;



    @GetMapping
    @Operation(summary = "get all bookmark (for current user)")
    public ResponseEntity<?> getAllBookmarkCurrentId() {
        GenericResponse genericResponse;
        try {
            Bookmark bookmark = Bookmark.builder()
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .build();

            List<BookmarkResponse> bookmarklist = bookmarkService.getAllBookmarkByCurrentId(weAreKhmerCurrentUser.getUserId());
            genericResponse = GenericResponse.builder()
                    .status("200")
                    .payload(bookmarklist)
                    .title("success")
                    .message("You have successfully got all bookmark recorded")
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


    @PostMapping
    @Operation(summary = "insert bookmark (for current user)")
    public ResponseEntity<?> insertBookmark(String articleId) {
        GenericResponse genericResponse;
        BookmarkRequest requestInsert = BookmarkRequest.builder()
                .userId(weAreKhmerCurrentUser.getUserId())
                .articleId(articleId)
                .build();
        weAreKhmerValidation.validateArticleId(articleId);
        try {

                Bookmark bookmark = Bookmark.builder()
                        .userId(weAreKhmerCurrentUser.getUserId())
                        .articleId(articleId)
                        .build();
            if(bookmarkService.getAllBookmarkCurrentId(articleId, weAreKhmerCurrentUser.getUserId())){
                Bookmark bookmark1 = bookmarkService.deleteBookmarkByArticleId(bookmark);
                genericResponse =
                        GenericResponse.builder()
                                .status("200")
                                .payload(bookmark1)
                                .title("success")
                                .message("You have deleted bookmark successfully")
                                .build();
                return ResponseEntity.ok(genericResponse);
            }
            Bookmark bookmark1 = bookmarkService.insertBookmark(bookmark);
            genericResponse =
                    GenericResponse.builder()
                            .status("200")
                            .payload(bookmark1)
                            .title("success")
                            .message("You have saved to bookmark successfully")
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

    @DeleteMapping("/Bookmark")
    @Operation(summary = "delete bookmark (for current user)")
    public ResponseEntity<?> deleteBookmark(String bookmarkId) {
        GenericResponse genericResponse;
        Bookmark bookmark = Bookmark.builder()
                .userId(weAreKhmerCurrentUser.getUserId())
                .bookmarkId(bookmarkId)
                .build();
        weAreKhmerValidation.validateBookmarkId(bookmarkId,weAreKhmerCurrentUser.getUserId());
        try{
                Bookmark bookmark1 = bookmarkService.deleteBookmark(bookmark);

                genericResponse = GenericResponse.builder()
                        .status("200")
                        .message("You have deleted bookmark record successfully")
                        .payload(bookmark1)
                        .title("success")
                        .build();
                return ResponseEntity.ok(genericResponse);

        }catch(Exception ex){
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


    @DeleteMapping("/Bookmarks")
    @Operation(summary = "delete all bookmark (for current user)")
    public ResponseEntity<?> removeAllBookmark() {
        GenericResponse genericResponse;
        try{
            Bookmark bookmark = Bookmark.builder()
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .build();

            List<BookmarkResponse> bookmarkResponseList = bookmarkService.getAllBookmarkByCurrentId(weAreKhmerCurrentUser.getUserId());

            if(bookmarkResponseList.size()>0){
                List<Bookmark> bookmark1 = bookmarkService.removeAllBookmark(bookmark);

                genericResponse = GenericResponse.builder()
                        .status("200")
                        .message("You have deleted bookmark record successfully")
                        .payload(bookmark1)
                        .title("success")
                        .build();
                return ResponseEntity.ok(genericResponse);
            }
            genericResponse = GenericResponse.builder()
                    .status("404")
                    .message("You don't have any bookmark records")
                    .title("Empty bookmark")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(genericResponse);

        }catch(Exception ex){
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
