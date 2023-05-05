package com.kshrd.wearekhmer.user.controller;



import com.kshrd.wearekhmer.user.model.dto.UserAppDTO;
import com.kshrd.wearekhmer.user.model.entity.UserApp;
import com.kshrd.wearekhmer.user.service.UserAppService;
import com.kshrd.wearekhmer.utils.userUtil.UserUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class UserController {
    private final UserAppService userAppService;
    private final UserUtil userUtil;


    @GetMapping("/hello")
    public String userApps () {
        return "SpringBoot hello.";
    }
    @GetMapping("/{email}")
    public ResponseEntity<?> userApps (@PathVariable String email) {
        UserApp userApp = userAppService.findUserByEmail(email);
        UserAppDTO userAppDTO = userUtil.toUserAppDTO(userApp);
        if (userAppDTO != null) {
            return ResponseEntity.ok(userAppDTO);
        }
        return ResponseEntity.ok("Hi");
    }

}
