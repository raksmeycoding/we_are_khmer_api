package com.kshrd.wearekhmer.history.controller;

import com.kshrd.wearekhmer.article.controller.IArticleController;
import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.article.service.IArticleService;
import com.kshrd.wearekhmer.history.model.entity.History;
import com.kshrd.wearekhmer.history.model.request.HistoryRequest;
import com.kshrd.wearekhmer.history.model.response.HistoryResponse;
import com.kshrd.wearekhmer.history.service.IHistoryService;
import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/v1/history")
@SecurityRequirement(name = "bearerAuth")

@AllArgsConstructor
public class HistoryControllerImp implements IHistoryController {

    private final IHistoryService historyService;
    private final IArticleService articleService;
    private WeAreKhmerCurrentUser weAreKhmerCurrentUser;


    @Override
    @GetMapping
    @Operation(summary = "get all history (for current user)")
    public ResponseEntity<?> getAllHistoryByCurrentId() {
        GenericResponse genericResponse;
        try {
            History history1 = History.builder()
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .build();

            List<HistoryResponse> history2 = historyService.getAllHistoryByCurrentUser(weAreKhmerCurrentUser.getUserId());
            genericResponse = GenericResponse.builder()
                    .status("200")
                    .payload(history2)
                    .title("success")
                    .message("You have successfully got all history recorded")
                    .build();
            return ResponseEntity.ok(genericResponse);

        }catch (Exception ex){
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
    @Operation(summary = "insert history (for current user) ")
    public ResponseEntity<?> insertHistory(String articleId) {
        GenericResponse genericResponse;
        try {


            HistoryRequest requestInsert = HistoryRequest.builder()
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .articleId(articleId)
                    .build();

            if(historyService.getAllHistoryByCurrentId(articleId, weAreKhmerCurrentUser.getUserId()) == true){
                History update = historyService.updateHistory(articleId, weAreKhmerCurrentUser.getUserId());
                genericResponse = GenericResponse.builder()
                        .status("200")
                        .payload(update)
                        .title("success")
                        .message("You have update this record successfully")
                        .build();
                return ResponseEntity.ok(genericResponse);
            }else{
                History history = History.builder()
                        .userId(weAreKhmerCurrentUser.getUserId())
                        .articleId(articleId)
                        .build();
                History history1 = historyService.insertHistory(history);
                genericResponse = GenericResponse.builder()
                        .status("200")
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
                            .status("500")
                            .message(ex.getMessage())
                            .build();
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(genericResponse);
        }
    }

    @Override
    @DeleteMapping("/history")
    @Operation(summary = "delete history (for current user) ")
    public ResponseEntity<?> deleteHistory(String historyId) {
        GenericResponse genericResponse;
        try {
            History history = History.builder()
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .historyId(historyId)
                    .build();

            History history1 = historyService.deleteHistory(history);


            genericResponse = GenericResponse.builder()
                    .status("200")
                    .message("You have deleted history record successfully")
                    .payload(history1)
                    .title("success")
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
    @DeleteMapping("histories")
    @Operation(summary = "delete all history (for current user) ")
    public ResponseEntity<?> removeAllHistory() {
        GenericResponse genericResponse;
        try {
            History history = History.builder()
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .build();

            List<History> history1 = historyService.removeAllHistory(history);

            genericResponse = GenericResponse.builder()
                    .status("200")
                    .message("You have deleted history record successfully")
                    .payload(history1)
                    .title("success")
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

}
