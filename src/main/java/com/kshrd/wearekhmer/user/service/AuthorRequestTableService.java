package com.kshrd.wearekhmer.user.service;

import com.kshrd.wearekhmer.user.model.entity.AuthorRequestTable;

import java.util.List;

public interface AuthorRequestTableService {
    List<AuthorRequestTable> getAll();
    List<AuthorRequestTable> getAllByUserId(String userId);
    AuthorRequestTable getById(String authorRequestId);
    AuthorRequestTable insert(AuthorRequestTable authorRequest);
    AuthorRequestTable update(AuthorRequestTable authorRequest);
    AuthorRequestTable delete(String authorRequestId);
}
