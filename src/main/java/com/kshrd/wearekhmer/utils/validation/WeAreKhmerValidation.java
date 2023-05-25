package com.kshrd.wearekhmer.utils.validation;

import com.kshrd.wearekhmer.bookmark.model.reponse.BookmarkResponse;
import com.kshrd.wearekhmer.history.model.entity.History;
import com.kshrd.wearekhmer.history.model.response.HistoryResponse;

import java.util.List;

public interface WeAreKhmerValidation {
    void validateElementInAList(List<?> list, Integer x, String mssErrSizeZero, String mssErrMaxSize);
    void validateElementLengthInAList(List<?> list, Integer x, String mssErrSizeZero, String mssErrMaxSize);

    void genderValidation(String gender);

    public boolean isAdmin();


    public boolean validateArticleId(String articleId);

    public boolean validateReportId(String reportId);

    public boolean validateHistoryId(String historyId);

    public List<HistoryResponse> validateHistoryRemoveAll(String userId);

    public boolean validateBookmarkId(String bookmarkId);

    public List<BookmarkResponse> validateBookmarkRemoveAll(String userId);
}
