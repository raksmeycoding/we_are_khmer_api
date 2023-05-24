package com.kshrd.wearekhmer.user.service;

import com.kshrd.wearekhmer.user.model.dto.AuthorDTO;
import com.kshrd.wearekhmer.user.model.entity.AuthorRequestTable;

import java.util.List;

public interface AuthorService {
    List<AuthorDTO> getAllAuthor();

    List<AuthorRequestTable> getAll();

    String updateUserRequestToBeAsAuthor(String userId);

    boolean updateUserRequestToBeAsAuthorAsReject(String userId);
}
