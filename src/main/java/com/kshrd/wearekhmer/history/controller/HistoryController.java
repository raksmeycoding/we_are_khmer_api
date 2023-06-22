package com.kshrd.wearekhmer.history.controller;

import com.kshrd.wearekhmer.article.service.ArticleService;
import com.kshrd.wearekhmer.history.model.entity.History;
import com.kshrd.wearekhmer.history.model.request.HistoryRequest;
import com.kshrd.wearekhmer.history.model.response.HistoryResponse;
import com.kshrd.wearekhmer.history.service.HistoryService;
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
@RequestMapping("/api/v1/history")
@SecurityRequirement(name = "bearerAuth")

@AllArgsConstructor
public class HistoryController {

    private final HistoryService historyService;
    private final ArticleService articleService;
    private WeAreKhmerCurrentUser weAreKhmerCurrentUser;

    private WeAreKhmerValidation weAreKhmerValidation;


    @GetMapping
    @Operation(summary = "get all history (for current user)")
    public ResponseEntity<?> getAllHistoryByCurrentId() {
        GenericResponse genericResponse;
        try {
            History history1 = History.builder()
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .build();

            List<HistoryResponse> history2 = historyService.getAllHistoryByCurrentUser(weAreKhmerCurrentUser.getUserId());
            if(history2.size()>0){
                genericResponse = GenericResponse.builder()
                        .statusCode(200)
                        .payload(history2)
                        .title("success")
                        .message("You have successfully got all history recorded")
                        .build();
                return ResponseEntity.ok(genericResponse);
            }else {
                genericResponse = GenericResponse.builder()
                        .message("There's no history records in your list")
                        .statusCode(404)
                        .title("failure")
                        .build();
                return ResponseEntity.ok(genericResponse);

            }



        } catch (Exception ex) {
            genericResponse =
                    GenericResponse
                            .builder()
                            .statusCode(500)
                            .message(ex.getMessage())
                            .build();
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(genericResponse);
        }
    }


    @PostMapping
    @Operation(summary = "insert history (for current user) ")
    public ResponseEntity<?> insertHistory(String articleId) {

        weAreKhmerValidation.validateArticleId(articleId);
        GenericResponse genericResponse;

        try {
            HistoryRequest requestInsert = HistoryRequest.builder()
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .articleId(articleId)
                    .build();

            if (historyService.getAllHistoryByCurrentId(articleId, weAreKhmerCurrentUser.getUserId())) {
                History update = historyService.updateHistory(articleId, weAreKhmerCurrentUser.getUserId());
                genericResponse = GenericResponse.builder()
                        .statusCode(201)
                        .payload(update)
                        .title("success")
                        .message("You have update this record successfully")
                        .build();
                return ResponseEntity.ok(genericResponse);
            } else {
                History history = History.builder()
                        .userId(weAreKhmerCurrentUser.getUserId())
                        .articleId(articleId)
                        .build();
                History history1 = historyService.insertHistory(history);
                genericResponse = GenericResponse.builder()
                        .statusCode(201)
                        .payload(history1)
                        .title("success")
                        .message("You have successfully recorded history")
                        .build();
                return ResponseEntity.ok(genericResponse);
            }
        } catch (Exception ex) {
            genericResponse =
                    GenericResponse
                            .builder()
                            .statusCode(500)
                            .message(ex.getMessage())
                            .build();
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(genericResponse);
        }
    }


    @DeleteMapping("/history")
    @Operation(summary = "delete history (for current user) ")
    public ResponseEntity<?> deleteHistory(String historyId) {
        History history = History.builder()
                .userId(weAreKhmerCurrentUser.getUserId())
                .historyId(historyId)
                .build();
        weAreKhmerValidation.validateHistoryId(historyId,weAreKhmerCurrentUser.getUserId());
        GenericResponse genericResponse;
        try {
            History history1 = historyService.deleteHistory(history);
            genericResponse = GenericResponse.builder()
                    .statusCode(200)
                    .message("You have deleted history record successfully")
                    .payload(history1)
                    .title("success")
                    .build();
            return ResponseEntity.ok(genericResponse);
        } catch (Exception ex) {
            genericResponse =
                    GenericResponse
                            .builder()
                            .statusCode(500)
                            .message(ex.getMessage())
                            .build();
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(genericResponse);
        }
    }


    @DeleteMapping("histories")
    @Operation(summary = "delete all history (for current user) ")
    public ResponseEntity<?> removeAllHistory() {
        GenericResponse genericResponse;
        try {
            History history = History.builder()
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .build();
            List<HistoryResponse> historyResponseList = historyService.getAllHistoryByCurrentUser(weAreKhmerCurrentUser.getUserId());

            if (historyResponseList.size() > 0) {
                List<History> history1 = historyService.removeAllHistory(history);
                genericResponse = GenericResponse.builder()
                        .statusCode(200)
                        .message("You have deleted history record successfully")
                        .payload(history1)
                        .title("success")
                        .build();
                return ResponseEntity.ok(genericResponse);
            }

            genericResponse = GenericResponse.builder()
                    .statusCode(404)
                    .message("You don't have any history records")
                    .title("failure")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(genericResponse);


        } catch (Exception ex) {
            genericResponse =
                    GenericResponse
                            .builder()
                            .statusCode(500)
                            .message(ex.getMessage())
                            .build();
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(genericResponse);
        }
    }

}
