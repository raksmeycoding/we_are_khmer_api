package com.kshrd.wearekhmer.user.service;


import com.kshrd.wearekhmer.user.model.entity.UserApp;
import com.kshrd.wearekhmer.request.NormalUserRequest;

public interface UserAppService {
//    UserApp findUserByUserName(String userName);
    UserApp findUserByEmail(String email);
//    Boolean existsByUserName(String userName);
//    Boolean existsByEmail(String email);


    UserApp normalUserRegister(NormalUserRequest normalUserRequest);


    String registerAsAuthorAndReturnUserId( String userId);
    UserApp registerAsAuthorAndReturnUserApp(String userId);
}
