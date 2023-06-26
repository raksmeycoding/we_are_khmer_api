package com.kshrd.wearekhmer.userRating;


import com.kshrd.wearekhmer.exception.ValidateException;
import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.userRating.Request.RatingRequest;
import com.kshrd.wearekhmer.userRating.dto.RatingDto;
import com.kshrd.wearekhmer.userRating.reponse.RatingBarResponse;
import com.kshrd.wearekhmer.userRating.reponse.RatingResponse;
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
@RequestMapping("/api/v1/rating")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class RatingController {


    private final IRatingService ratingService;
    private final WeAreKhmerCurrentUser weAreKhmerCurrentUser;
    private final WeAreKhmerValidation weAreKhmerValidation;


    @PostMapping
    @Operation(summary = "Rating author for current user. (only work when you are login), ❤️ Validated")
    public ResponseEntity<?> createUserRatingToAuthor(@RequestBody @Valid RatingRequest ratingRequest) {
        weAreKhmerValidation.checkAuthorExist(ratingRequest.getAuthor_id());
//        if(ratingRequest.getNumber_of_rating() > 6 || ratingRequest.getNumber_of_rating() <= 0){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "number_of_rating cannot ");
//        }

            RatingDto ratingDto = RatingDto.builder()
                    .user_id(weAreKhmerCurrentUser.getUserId())
                    .author_id(ratingRequest.getAuthor_id())
                    .number_of_rating(ratingRequest.getNumber_of_rating())
                    .build();
            Rating returnRating = ratingService.createUserRatingToAuthor(ratingDto);
            return ResponseEntity.ok(GenericResponse.builder()
                    .title("success")
                    .statusCode(201)
                    .message("Rating successfully.")
                    .payload(returnRating)
                    .build());


    }


    @GetMapping("{authorId}")
    @Operation(summary = "(Get total of rating for author by authorId)")
    private ResponseEntity<?> getRatingByAuthorId(@PathVariable String authorId) {
        weAreKhmerValidation.checkAuthorExist(authorId);
        try {
            RatingResponse ratingResponse = ratingService.getRatingByAuthorId(authorId);
            return ResponseEntity.ok(GenericResponse.builder().statusCode(200).message("Get total of rating for author by authorId successfully").title("success").payload(ratingResponse).build());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body(GenericResponse.builder()
                    .title("error")
                    .message(exception.getMessage())
                    .statusCode(500)
                    .build());
        }
    }


    @GetMapping("/rating-bar/{authorId}")
    @Operation(summary = "(Get rating bar by authorId)")
    private ResponseEntity<?> getRatingBarByAuthorId(@PathVariable String authorId) {
        weAreKhmerValidation.checkAuthorExist(authorId);
        List<RatingBarResponse> ratingBarResponseList = ratingService.getRatingBarByAuthorId(authorId);
        if(ratingBarResponseList.isEmpty()) {
            throw new ValidateException("No records had been found.", HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
        }
        return ResponseEntity.ok().body(GenericResponse.builder()
                .message("Get rating bar successfully.")
                .title("success")
                .statusCode(200)
                .payload(ratingBarResponseList)
                .build());

    }


    @GetMapping("/totalViewAllArticleRecords")
    @Operation(summary = "(Get total view articles all records by authorId)")
    public ResponseEntity<?> getTotalViewAllArticleRecordsByAuthorId(String authorId) {
        weAreKhmerValidation.checkAuthorExist(authorId);
        return ResponseEntity.ok().body(GenericResponse.builder()
                .message("Ge total view successfully")
                .title("success")
                .statusCode(200)
                .payload(ratingService.getTotalViewAllRecordsByAuthorId(authorId))
                .build());
    }




}
