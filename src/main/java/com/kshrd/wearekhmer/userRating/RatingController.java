package com.kshrd.wearekhmer.userRating;


import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.userRating.Request.RatingRequest;
import com.kshrd.wearekhmer.userRating.dto.RatingDto;
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
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/rating")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class RatingController {


    private final IRatingService ratingService;
    private final WeAreKhmerCurrentUser weAreKhmerCurrentUser;
    private final WeAreKhmerValidation weAreKhmerValidation;


    @PostMapping
    @Operation(summary = "Rating author for current user. (only work when you are login)")
    public ResponseEntity<?> createUserRatingToAuthor(@RequestBody @Valid RatingRequest ratingRequest) {
        weAreKhmerValidation.checkAuthorExist(ratingRequest.getAuthor_id());
        if(ratingRequest.getNumber_of_rating() > 6 || ratingRequest.getNumber_of_rating() <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "number_of_rating cannot ");
        }
        try {
            RatingDto ratingDto = RatingDto.builder()
                    .user_id(weAreKhmerCurrentUser.getUserId())
                    .author_id(ratingRequest.getAuthor_id())
                    .number_of_rating(ratingRequest.getNumber_of_rating())
                    .build();
            Rating returnRating = ratingService.createUserRatingToAuthor(ratingDto);
            return ResponseEntity.ok(GenericResponse.builder()
                    .title("success")
                    .status("200")
                    .message("Rating successfully.")
                    .payload(returnRating)
                    .build());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(GenericResponse.builder()
                    .message(ex.getMessage())
                    .status("500")
                    .build());
        }

    }


    @GetMapping("{authorId}")
    private ResponseEntity<?> getRatingByAuthorId(@PathVariable String authorId) {
        weAreKhmerValidation.checkAuthorExist(authorId);
        try {
            RatingResponse ratingResponse = ratingService.getRatingByAuthorId(authorId);
            return ResponseEntity.ok(GenericResponse.builder().status("200").message("Get rating by author successfully").title("success").payload(ratingResponse).build());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body(GenericResponse.builder()
                    .title("error")
                    .message(exception.getMessage())
                    .status("500")
                    .build());
        }
    }
}
