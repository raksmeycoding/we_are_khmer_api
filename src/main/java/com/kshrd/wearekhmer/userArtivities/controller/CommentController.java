package com.kshrd.wearekhmer.userArtivities.controller;


import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.userArtivities.model.UserComment;
import com.kshrd.wearekhmer.userArtivities.model.request.CommentRequest;
import com.kshrd.wearekhmer.userArtivities.service.ICommentService;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment/article")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class CommentController implements ICommentController {
    private final ICommentService commentService;
    private final WeAreKhmerCurrentUser weAreKhmerCurrentUser;

    @Override
    @GetMapping("/{articleId}")
    public ResponseEntity<?> getUserCommentByArticleId(@PathVariable String articleId) {
        GenericResponse genericResponse;
        try {
            List<UserComment> userCommentList = commentService.getUserCommentByArticleId(articleId);
            genericResponse = GenericResponse.builder()
                    .message("get successfully")
                    .payload(userCommentList)
                    .title("success")
                    .status("200")
                    .build();
            return ResponseEntity.ok(genericResponse);
        } catch (Exception ex) {
            genericResponse =
                    GenericResponse.builder()
                            .status("500")
                            .title("failed request")
                            .payload(ex.getMessage())
                            .build();
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(genericResponse);

        }
    }


    @Override
    @PostMapping
    public ResponseEntity<?> creatArticleComment(@RequestBody CommentRequest commentRequest) {
        GenericResponse genericResponse;
        try {
            UserComment userComment = commentService.creatArticleComment(weAreKhmerCurrentUser.getUserId(), commentRequest.getArticleId(), commentRequest.getComment());
            genericResponse = GenericResponse.builder()
                    .title("success")
                    .message("comment success")
                    .status("200")
                    .payload(userComment)
                    .build();

            return ResponseEntity.ok(genericResponse);
        } catch (Exception ex) {
            genericResponse = GenericResponse.builder()
                    .message(ex.getMessage())
                    .title("comment failed")
                    .build();
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(genericResponse);
        }
    }
}
