package com.kshrd.wearekhmer.userReviewAuthor.controller;


import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.userReviewAuthor.model.entity.UserReviewAuthor;
import com.kshrd.wearekhmer.userReviewAuthor.model.request.UserReviewAuthorRequest;
import com.kshrd.wearekhmer.userReviewAuthor.service.IUserReviewAuthorService;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/review")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class UserReviewAuthorController {
    private final WeAreKhmerCurrentUser weAreKhmerCurrentUser;
    private final IUserReviewAuthorService userReviewAuthorService;

    @PostMapping
    @Operation(summary = "(Review author for specific author.)")
    public ResponseEntity<?> insertUserReviewAuthorByCurrentUserId(@RequestBody UserReviewAuthorRequest userReviewAuthorRequest) {
        try {
            UserReviewAuthor userReviewAuthor = UserReviewAuthor.builder()
                    .user_id(weAreKhmerCurrentUser.getUserId())
                    .author_id(userReviewAuthorRequest.getAuthor_id())
                    .comment(userReviewAuthorRequest.getComment())
                    .build();

            UserReviewAuthor insertedUserReviewAuthor = userReviewAuthorService.insertUserReviewAuthorByCurrentUserId(userReviewAuthor);
            return ResponseEntity.ok().body(GenericResponse.builder()
                    .title("success")
                    .message("Insert succcess")
                    .status("200")
                    .payload(insertedUserReviewAuthor)
                    .build());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(GenericResponse.builder()
                    .title("error.")
                    .status("500")
                    .message(ex.getMessage())
                    .build());
        }
    }


    @GetMapping("{authorId}")
    public ResponseEntity<?> getAllUserReviewAuthorByAuthorId(@PathVariable String authorId) {
        try {
            return ResponseEntity.ok().body(GenericResponse.builder()
                    .message("Get data success fully.")
                    .status("200")
                    .payload(userReviewAuthorService.getAllUserReviewAuthorByAuthorId(authorId))
                    .title("success")
                    .build());
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body(GenericResponse.builder()
                    .status("500")
                    .title("Interna server error")
                    .message(exception.getMessage())
                    .build());
        }
    }
}