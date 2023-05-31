package com.kshrd.wearekhmer.utils.validation;


import com.kshrd.wearekhmer.Category.repository.CategoryMapper;
import com.kshrd.wearekhmer.article.service.ArticleService;
import com.kshrd.wearekhmer.bookmark.model.reponse.BookmarkResponse;
import com.kshrd.wearekhmer.bookmark.repository.BookmarkMapper;
import com.kshrd.wearekhmer.exception.CustomRuntimeException;
import com.kshrd.wearekhmer.history.model.response.HistoryResponse;
import com.kshrd.wearekhmer.history.repository.HistoryMapper;
import com.kshrd.wearekhmer.user.repository.UserAppRepository;
import com.kshrd.wearekhmer.userReport.repository.ReportMapper;
import com.kshrd.wearekhmer.utils.WeAreKhmerConstant;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    private final ArticleService articleService;

    private final HistoryMapper historyMapper;

    private final BookmarkMapper bookmarkMapper;

    private final CategoryMapper categoryMapper;

    private final UserAppRepository userAppRepository;


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
        if(!articleService.isArticleExist(articleId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article ID : "+articleId+ " does not exists");
        }
        return articleService.isArticleExist(articleId);

    }

    @Override
    public boolean validateReportId(String reportId) {
        return reportMapper.isReportExistByIs(reportId);
    }

    @Override
    public boolean validateHistoryId(String historyId, String userId) {
        if(!historyMapper.validateHistoryId(historyId,userId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User have no History ID : "+ historyId );
        }
        return historyMapper.validateHistoryId(historyId, userId);
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


    @Override
    public void validateTypeFileUpload(String type) {
        boolean matchCondition = false;
        String[] listFileUploadType = {"CATEGORY", "ARTICLE", "USER"};
        for (String t : listFileUploadType) {
            if (type.equalsIgnoreCase(t)) {
                matchCondition = true;
                break;
            }
        }

        if (!matchCondition) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid part variable type");
        }

    }


    @Override
    public void validateTypeFileUploadAndIdWithType(String type, String id) {
        validateTypeFileUpload(type);
        if (type.equalsIgnoreCase("article")) {
            boolean isExist = validateArticleId(id);
            if (!isExist) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article with id: " + id + " is not found!");
            }
        } else if (type.equalsIgnoreCase("category")) {
            boolean isExist = categoryMapper.isCategoryExist(id);
            if (!isExist) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with id: " + id + " is not found!");
            }
        } else if (type.equalsIgnoreCase("user")) {
            boolean isExist = userAppRepository.userExist(id);
            if (!isExist) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id :" + id + " is not found!");
            }
        }
    }
}




