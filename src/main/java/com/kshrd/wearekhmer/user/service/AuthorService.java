package com.kshrd.wearekhmer.user.service;

import com.kshrd.wearekhmer.user.model.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    List<AuthorDTO> getAllAuthor();

    boolean updateUserRequestToBeAsAuthor(String userId);

    boolean updateUserRequestToBeAsAuthorAsReject(String userId);
}
