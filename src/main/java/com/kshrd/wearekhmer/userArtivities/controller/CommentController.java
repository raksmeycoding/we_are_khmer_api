package com.kshrd.wearekhmer.userArtivities.controller;


import com.kshrd.wearekhmer.exception.ValidateException;
import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.userArtivities.model.UserComment;
import com.kshrd.wearekhmer.userArtivities.model.dto.AuthorReplyCommentMapper;
import com.kshrd.wearekhmer.userArtivities.model.request.AuthorReplyCommentRequest;
import com.kshrd.wearekhmer.userArtivities.model.request.CommentRequest;
import com.kshrd.wearekhmer.userArtivities.service.ICommentService;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comment/article")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class CommentController implements ICommentController {
    private final ICommentService commentService;
    private final WeAreKhmerCurrentUser weAreKhmerCurrentUser;
    private final WeAreKhmerValidation weAreKhmerValidation;

    @Override
    @GetMapping("/{articleId}")
    @Operation(summary = "(Get all comment by article id)")
    public ResponseEntity<?> getUserCommentByArticleId(@Valid @PathVariable String articleId) {
        GenericResponse genericResponse;
        weAreKhmerValidation.validateArticleId(articleId);
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
    @Operation(summary = "(User can create a comment to article)")
    public ResponseEntity<?> creatArticleComment(@RequestBody @Valid CommentRequest commentRequest) {
        GenericResponse genericResponse;
        weAreKhmerValidation.validateArticleId(commentRequest.getArticleId());
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


    @PostMapping("/reply")
    @Operation(summary = "(Author can we replay comment to his article).")
    public ResponseEntity<?> authorReplyComment(@RequestBody AuthorReplyCommentRequest authorReplyCommentRequest) {
        boolean status = commentService.validateParentIdExist(authorReplyCommentRequest.getComment_id());
        if (status == true) {
            throw new ValidateException("This comment had been replied.", HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.value());
        }
        try {
            AuthorReplyCommentMapper authorReplyCommentMapper = AuthorReplyCommentMapper.builder()
                    .comment_id(authorReplyCommentRequest.getComment_id())
                    .comment(authorReplyCommentRequest.getComment())
                    .build();
            UserComment userComment = commentService.authorReplyCommentToHisArticle(authorReplyCommentMapper);
            if (userComment == null) {
                throw new ValidateException("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
            return ResponseEntity.ok().body(GenericResponse.builder()
                    .message("You had replied comment successfully.")
                    .title("success")
                    .build());
        } catch (Exception ex) {
            if (ex.getCause() instanceof SQLException) {
                if (((SQLException) ex.getCause()).getSQLState().equals("23503")) {
                    throw new ValidateException("No comment with #id=" + authorReplyCommentRequest.getComment_id(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
                }
            }
            throw new RuntimeException(ex.getCause());
        }
    }
}
