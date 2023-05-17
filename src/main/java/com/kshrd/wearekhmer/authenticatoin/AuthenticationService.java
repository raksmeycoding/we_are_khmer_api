package com.kshrd.wearekhmer.authenticatoin;


import com.kshrd.wearekhmer.requestRequest.UserRepsonse;
import com.kshrd.wearekhmer.user.repository.UserAppRepository;
import com.kshrd.wearekhmer.security.JwtService;
import com.kshrd.wearekhmer.requestRequest.UserLoginRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Data
@Builder
@Service
public class AuthenticationService {
    private final JwtService jwtService;
    private final UserAppRepository userAppRepository;
    private final AuthenticationManager authenticationManager;

    public UserRepsonse authenticate(UserLoginRequest userLoginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword()));
        var user = userAppRepository.findUserByEmail(userLoginRequest.getEmail());
        var token = jwtService.generateToken(user);
        return UserRepsonse.builder().email(userLoginRequest.getEmail()).token(token).roles(userAppRepository.getUserRolesByUserEmail(userLoginRequest.getEmail())).build();
    }
}
