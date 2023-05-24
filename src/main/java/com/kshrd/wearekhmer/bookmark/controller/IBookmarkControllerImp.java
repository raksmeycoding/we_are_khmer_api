package com.kshrd.wearekhmer.bookmark.controller;

import com.kshrd.wearekhmer.article.service.IArticleService;
import com.kshrd.wearekhmer.bookmark.model.entity.Bookmark;
import com.kshrd.wearekhmer.bookmark.model.reponse.BookmarkResponse;
import com.kshrd.wearekhmer.bookmark.model.request.BookmarkRequest;
import com.kshrd.wearekhmer.bookmark.repository.BookmarkMapper;
import com.kshrd.wearekhmer.bookmark.service.IBookService;
import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/bookmark")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class IBookmarkControllerImp implements IBookmarkController {

    private final IBookService bookmarkService;
    private final IArticleService articleService;

    private WeAreKhmerCurrentUser weAreKhmerCurrentUser;


    @Override
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

    @Override
    @PostMapping
    @Operation(summary = "insert bookmark (for current user)")
    public ResponseEntity<?> insertBookmark(String articleId) {
        GenericResponse genericResponse;
        try {

            BookmarkRequest requestInsert = BookmarkRequest.builder()
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .articleId(articleId)
                    .build();

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
    @Override
    @DeleteMapping("/Bookmark")
    @Operation(summary = "delete bookmark (for current user)")
    public ResponseEntity<?> deleteBookmark(String bookmarkId) {
        GenericResponse genericResponse;
        try{
            Bookmark bookmark = Bookmark.builder()
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .bookmarkId(bookmarkId)
                    .build();

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

    @Override
    @DeleteMapping("/Bookmarks")
    @Operation(summary = "delete all bookmark (for current user)")
    public ResponseEntity<?> removeAllBookmark() {
        GenericResponse genericResponse;
        try{
            Bookmark bookmark = Bookmark.builder()
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .build();

            List<Bookmark> bookmark1 = bookmarkService.removeAllBookmark(bookmark);

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
}
