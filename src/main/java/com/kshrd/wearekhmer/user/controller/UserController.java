package com.kshrd.wearekhmer.user.controller;


import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.user.model.dto.UserAppDTO;
import com.kshrd.wearekhmer.user.model.entity.UpdateUserName;
import com.kshrd.wearekhmer.user.model.entity.UserApp;
import com.kshrd.wearekhmer.user.model.entity.Users;
import com.kshrd.wearekhmer.user.repository.UserAppRepository;
import com.kshrd.wearekhmer.user.service.UserAppService;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import com.kshrd.wearekhmer.utils.serviceClassHelper.ServiceClassHelper;
import com.kshrd.wearekhmer.utils.userUtil.UserUtil;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class UserController {
    private final UserAppService userAppService;
    private final UserUtil userUtil;
    private final ServiceClassHelper serviceClassHelper;

    private final UserAppRepository userAppRepository;

    private final WeAreKhmerValidation weAreKhmerValidation;

    private final WeAreKhmerCurrentUser weAreKhmerCurrentUser;

    private static final Integer PAGE_SIZE = 10;




    private Integer getNextPage(Integer page) {
        int numberOfRecord = serviceClassHelper.getTotalOfRecordInArticleTb();
        System.out.println(numberOfRecord);
        int totalPage = (int) Math.ceil((double) numberOfRecord / PAGE_SIZE);
        System.out.println(totalPage);
        if (page > totalPage) {
            page = totalPage;
        }
        weAreKhmerValidation.validatePageNumber(page);
        return (page - 1) * PAGE_SIZE;
    }



    @GetMapping
    @Operation(summary = "(Get all user - user is not band)")
    public ResponseEntity<?> getAllUser() {
        List<UserApp> userAppList = userAppService.getAllUser();
        List<UserAppDTO> userAppDTOList = userAppList.stream().map(userUtil::toUserAppDTO).collect(Collectors.toList());
        return ResponseEntity.ok().body(GenericResponse.builder()
                .status("200")
                .title("success")
                .message("Get user data successfully!")
                .payload(userAppDTOList)
                .build());
    }

    @GetMapping("/hello")
    @Hidden
    public String userApps() {
        return "SpringBoot hello.";
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> userApps(@PathVariable String email) {
        UserApp userApp = userAppService.findUserByEmail(email);
        UserAppDTO userAppDTO = userUtil.toUserAppDTO(userApp);
        if (userAppDTO != null) {
            return ResponseEntity.ok(userAppDTO);
        }
        return ResponseEntity.ok("Hi");
    }

    @GetMapping("/admin/getAllUser/{isAuthor}")
    @Operation(summary = "Get all user both author and user")
    public ResponseEntity<?> getAllAuthorUser(@RequestParam(defaultValue = "1", required = false) Integer page, @PathVariable("isAuthor") boolean isAuthor){
        GenericResponse genericResponse;
        Integer total = userAppRepository.countUserOrAuthor(isAuthor);
        Integer nextPage = getNextPage(page);
        List<Users> users = userAppService.getUserOrAuthor(isAuthor,PAGE_SIZE,nextPage);
        if(users != null){
            genericResponse = GenericResponse.builder()
                    .title("success")
                    .statusCode(200)
                    .totalRecords(total)
                    .message("You have successfully get data")
                    .payload(users)
                    .build();
            return ResponseEntity.ok(genericResponse);
        }

        genericResponse = GenericResponse.builder()
                .title("failure")
                .message("There's no data")
                .statusCode(404)
                .build();

        return ResponseEntity.ok(genericResponse);

    }

    @PutMapping("/updateName")
    @Operation(summary = "Update username for current user")
    public ResponseEntity<?> updateName(@RequestBody @Valid UpdateUserName user){

        UpdateUserName updateUserName = userAppService.UpdateUserName(user.getUserName(), weAreKhmerCurrentUser.getUserId());

        GenericResponse genericResponse = GenericResponse.builder()
                .title("success")
                .message("You have successfully updated your name")
                .statusCode(200)
                .payload(updateUserName)
                .build();

        return ResponseEntity.ok(genericResponse);

    }

}
