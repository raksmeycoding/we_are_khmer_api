package com.kshrd.wearekhmer.utils.userUtil;

import com.kshrd.wearekhmer.user.model.dto.UserAppDTO;
import com.kshrd.wearekhmer.user.model.entity.UserApp;
import lombok.Builder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Builder
@Component
public class UserUtil {

    private final ModelMapper modelMapper;

    public UserUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserAppDTO toUserAppDTO(UserApp userApp) {
        UserAppDTO userAppDTO =  modelMapper.map(userApp, UserAppDTO.class);
        return userAppDTO;
    }

    public UserApp toUserApp(UserAppDTO userAppDTO) {
        UserApp userApp = modelMapper.map(userAppDTO, UserApp.class);
        return userApp;
    }


}
