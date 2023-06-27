package com.kshrd.wearekhmer.user.service;


import com.kshrd.wearekhmer.user.model.dto.UserAppDTO;
import com.kshrd.wearekhmer.user.model.entity.Users;
import com.kshrd.wearekhmer.user.model.entity.UserApp;
import com.kshrd.wearekhmer.requestRequest.NormalUserRequest;

import java.util.List;

public interface UserAppService {
//    UserApp findUserByUserName(String userName);
    UserApp findUserByEmail(String email);
//    Boolean existsByUserName(String userName);
//    Boolean existsByEmail(String email);


    UserApp normalUserRegister(NormalUserRequest normalUserRequest);


    String registerAsAuthorAndReturnUserId( String userId);
    UserApp registerAsAuthorAndReturnUserApp(String userId);


    List<UserApp> getAllUser();

    List<Users> getUserOrAuthor(boolean isAuthor, Integer pageNumber, Integer nextPage);
}
