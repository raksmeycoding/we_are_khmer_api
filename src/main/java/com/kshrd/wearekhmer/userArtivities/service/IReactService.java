package com.kshrd.wearekhmer.userArtivities.service;

import com.kshrd.wearekhmer.userArtivities.model.React;

public interface IReactService {
    React createUserReactForCurrentUser(React react);
    React deleteUserReactForCurrentUser(React react);
}
