package com.kshrd.wearekhmer.user.controller;


import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.user.model.dto.UserAppDTO;
import com.kshrd.wearekhmer.user.model.entity.UserApp;
import com.kshrd.wearekhmer.user.service.UserAppService;
import com.kshrd.wearekhmer.utils.userUtil.UserUtil;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class UserController {
    private final UserAppService userAppService;
    private final UserUtil userUtil;


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

}
