package com.kshrd.wearekhmer.user.service.userService;


import com.kshrd.wearekhmer.user.model.dto.UserAppDTO;
import com.kshrd.wearekhmer.user.model.entity.Users;
import com.kshrd.wearekhmer.user.model.entity.UserApp;
import com.kshrd.wearekhmer.requestRequest.NormalUserRequest;
import com.kshrd.wearekhmer.user.repository.UserAppRepository;
import com.kshrd.wearekhmer.user.service.UserAppService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class UserAppDetailsServiceImpl implements UserAppService, UserDetailsService {
    private UserAppRepository userAppRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userApp = userAppRepository.findUserByEmail(username);
        if(userApp == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return userApp;
    }

//    @Override
//    public UserApp findUserByUserName(String userName) {
//        return null;
//    }

    @Override
    public UserApp findUserByEmail(String email) {
        return userAppRepository.findUserByEmail(email);
    }




//    @Override
//    public Boolean existsByUserName(String userName) {
//        return null;
//    }
//
//    @Override
//    public Boolean existsByEmail(String email) {
//        return null;
//    }


    @Override
    public UserApp normalUserRegister(NormalUserRequest normalUserRequest) {
        return userAppRepository.normalUserRegister(normalUserRequest);
    }

    @Override
    public UserApp registerAsAuthorAndReturnUserApp(String userId) {
        return userAppRepository.registerAsAuthorAndReturnUserApp(userId);
    }

    @Override
    public String registerAsAuthorAndReturnUserId(String userId) {
        return userAppRepository.registerAsAuthorAndReturnUserId(userId);
    }


    @Override
    public List<UserApp> getAllUser() {
        return userAppRepository.getAllUser();
    }

    @Override
    public List<Users> getUserOrAuthor(boolean isAuthor, Integer pageNumber, Integer nextPage) {
        return userAppRepository.dynamicUserAndAuthor(isAuthor,pageNumber,nextPage);
    }
}
