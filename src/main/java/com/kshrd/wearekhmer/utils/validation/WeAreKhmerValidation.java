package com.kshrd.wearekhmer.utils.validation;

import com.kshrd.wearekhmer.Category.model.Category;
import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.article.model.request.ArticleRequest;
import com.kshrd.wearekhmer.bookmark.model.reponse.BookmarkResponse;
import com.kshrd.wearekhmer.history.model.response.HistoryResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.UUID;

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
















}
