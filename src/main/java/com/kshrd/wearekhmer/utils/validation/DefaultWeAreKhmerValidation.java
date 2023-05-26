package com.kshrd.wearekhmer.utils.validation;


import com.kshrd.wearekhmer.article.repository.ArticleMapper;
import com.kshrd.wearekhmer.article.service.IArticleService;
import com.kshrd.wearekhmer.bookmark.model.reponse.BookmarkResponse;
import com.kshrd.wearekhmer.bookmark.repository.BookmarkMapper;
import com.kshrd.wearekhmer.exception.CustomRuntimeException;
import com.kshrd.wearekhmer.history.model.response.HistoryResponse;
import com.kshrd.wearekhmer.history.repository.HistoryMapper;
import com.kshrd.wearekhmer.userReport.repository.ReportMapper;
import com.kshrd.wearekhmer.utils.WeAreKhmerConstant;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
@Qualifier("DefaultWeAreKhmerValidation")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class DefaultWeAreKhmerValidation implements WeAreKhmerValidation {

    private final WeAreKhmerConstant weAreKhmerConstant;
    private WeAreKhmerCurrentUser weAreKhmerCurrentUser;

    private final ReportMapper reportMapper;

    private final IArticleService articleService;

    private final HistoryMapper historyMapper;

    private final BookmarkMapper bookmarkMapper;






    @Override
    public void validateElementInAList(List<?> list, Integer x, String mssErrSizeZero, String mssErrMaxSize) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(mssErrSizeZero);
        }
        if (list.size() > x) {
            throw new IllegalArgumentException(mssErrMaxSize);
        }

    }


    @Override
    public void validateElementLengthInAList(List<?> list, Integer x, String mssErrSizeZero, String mssErrMaxSize) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(mssErrSizeZero);
        }
        if (list.size() > x) {
            throw new IllegalArgumentException(mssErrMaxSize);
        }
    }


    @Override
    public void genderValidation(String gender) {
        for (String g : weAreKhmerConstant.GENDER) {
            if (gender.equals(g)) {
                return;
            }
            throw new CustomRuntimeException("Gender must be lowercase and be formatted in (male, female, other).");

        }
    }


    private boolean validate(String password) {
        Pattern pattern = Pattern.compile(weAreKhmerConstant.PASSWORD_PATTERN);
        return pattern.matcher(password).matches();
    }


    public void passwordValidation(String password) {
        if (password.length() < 8) {
            throw new CustomRuntimeException("Password must be at least 8 characters");
        }
    }


    @Override
    @SecurityRequirement(name = "bearerAuth")
    public boolean isAdmin() {

        System.out.println(weAreKhmerCurrentUser.getAuthorities());
        for (String role : weAreKhmerCurrentUser.getAuthorities()) {
            if (role.equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void validatePageNumber(Integer page) {
        boolean is = page >= 0 && page != 0;
        if (!is) {
            throw new CustomRuntimeException("Page number must be grater than 0 and not equal zero.");
        }
    }

    @Override
    public boolean validateArticleId(String articleId) {
        return articleService.isArticleExist(articleId);
    }

    @Override
    public boolean validateReportId(String reportId) {
        return reportMapper.isReportExistByIs(reportId);
    }

    @Override
    public boolean validateHistoryId(String historyId) {
        return historyMapper.validateHistoryId(historyId);
    }

    @Override
    public List<HistoryResponse> validateHistoryRemoveAll(String userId) {
        return historyMapper.getAllHistoryByCurrentUser(userId);
    }

    @Override
    public boolean validateBookmarkId(String bookmarkId) {
        return bookmarkMapper.validateBookmarkId(bookmarkId);
    }

    @Override
    public List<BookmarkResponse> validateBookmarkRemoveAll(String userId) {
        return bookmarkMapper.getAllBookmarkByCurrentId(userId);
    }


}




