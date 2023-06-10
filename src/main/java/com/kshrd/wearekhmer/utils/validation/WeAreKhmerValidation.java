package com.kshrd.wearekhmer.utils.validation;

import com.kshrd.wearekhmer.bookmark.model.reponse.BookmarkResponse;
import com.kshrd.wearekhmer.history.model.response.HistoryResponse;

import java.util.List;

public interface WeAreKhmerValidation {
    void validateElementInAList(List<?> list, Integer x, String mssErrSizeZero, String mssErrMaxSize);

    void validateElementLengthInAList(List<?> list, Integer x, String mssErrSizeZero, String mssErrMaxSize);

    void genderValidation(String gender);

    public boolean isAdmin();


    public boolean validateArticleId(String articleId);

    public boolean validateReportId(String reportId);

    public boolean validateHistoryId(String historyId, String userId);

    public void validatePageNumber(Integer page);

    public List<HistoryResponse> validateHistoryRemoveAll(String userId);

    public boolean validateBookmarkId(String bookmarkId, String userId);

    public List<BookmarkResponse> validateBookmarkRemoveAll(String userId);

    void validateTypeFileUpload(String type);

    void validateTypeFileUploadAndIdWithType(String type, String id);

    boolean validateCategoryId(String categoryId);

    boolean validateArticleIdByCurrentUser(String articleId, String userId);

    boolean checkAuthorExist(String authorId);

    boolean checkCategoryNameExist(String categoryName);

    void validateAdminIsRejectOrApprove(String status);

    boolean checkArticleByCategoryId(String categoryId, String articleId);

    boolean checkIndexIsExist(Integer index);

    boolean checkHeroCard(String categoryId, Integer index, String articleId);

    boolean validate3ArticleInCategoryId(String categoryId);

    boolean checkIsCategoryIdOutOfIndex(String categoryId, Integer index);

//    boolean checkHeroCardId(String heroCardId);
    boolean validateNotificationId(String userId, String notificationId);













}
