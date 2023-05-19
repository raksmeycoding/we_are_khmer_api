package com.kshrd.wearekhmer.userArtivities.service;

import com.kshrd.wearekhmer.userArtivities.model.React;
import com.kshrd.wearekhmer.userArtivities.repository.IReactRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor

public class ReactServiceImpl implements IReactService{
    private final IReactRepository reactRepository;
    @Override
    public React createUserReactForCurrentUser(React react) {
        return reactRepository.createUserReactForCurrentUser(react);
    }


    @Override
    public React deleteUserReactForCurrentUser(React react) {
        return reactRepository.deleteUserReactForCurrentUser(react);
    }
}
