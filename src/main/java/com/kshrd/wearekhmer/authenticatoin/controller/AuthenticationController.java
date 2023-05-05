package com.kshrd.wearekhmer.authenticatoin.controller;


import com.kshrd.wearekhmer.authenticatoin.AuthenticationService;
import com.kshrd.wearekhmer.user.model.dto.UserAppDTO;
import com.kshrd.wearekhmer.user.model.entity.UserApp;
import com.kshrd.wearekhmer.request.NormalUserRequest;
import com.kshrd.wearekhmer.request.UserLoginRequest;
import com.kshrd.wearekhmer.user.service.UserAppDetailsServiceImpl;
import com.kshrd.wearekhmer.utils.userUtil.UserUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class AuthenticationController {
    private final UserAppDetailsServiceImpl userAppDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    private final UserUtil userUtil;

    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<UserAppDTO> userRegister(@RequestBody NormalUserRequest normalUserRequest ) {
        NormalUserRequest n = NormalUserRequest.builder()
                .email(normalUserRequest.getEmail())
                .password(passwordEncoder.encode(normalUserRequest.getPassword()))
                .gender(normalUserRequest.getGender())
                .build();
        UserApp n2 = userAppDetailsService.normalUserRegister(n);
        if(n2 == null){
            throw new RuntimeException("Custom runtime error!");
        }
        UserAppDTO userAppDTO = userUtil.toUserAppDTO(n2);
        return ResponseEntity.ok(userAppDTO);
    };

    @PostMapping("/register/as-author/user/{userId}")
    public ResponseEntity<UserAppDTO> registerAsAuthor(@PathVariable String userId) {
        UserApp userApp = userAppDetailsService.registerAsAuthorAndReturnUserApp(userId);
        UserAppDTO userAppDTO = userUtil.toUserAppDTO(userApp);
        return ResponseEntity.ok(userAppDTO);
    }



    @PostMapping("/login")
    private ResponseEntity<?> userLogin(@RequestBody UserLoginRequest userLoginRequest){
        return ResponseEntity.ok(authenticationService.authenticate(userLoginRequest));
    }
}
