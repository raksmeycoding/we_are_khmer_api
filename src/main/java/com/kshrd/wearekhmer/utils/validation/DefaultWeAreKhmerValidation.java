package com.kshrd.wearekhmer.utils.validation;


import com.kshrd.wearekhmer.Category.repository.CategoryMapper;
import com.kshrd.wearekhmer.article.repository.ArticleMapper;
import com.kshrd.wearekhmer.article.service.ArticleService;
import com.kshrd.wearekhmer.bookmark.model.reponse.BookmarkResponse;
import com.kshrd.wearekhmer.bookmark.repository.BookmarkMapper;
import com.kshrd.wearekhmer.exception.CustomRuntimeException;
import com.kshrd.wearekhmer.exception.ValidateException;
import com.kshrd.wearekhmer.heroCard.repository.HeroCardRepository;
import com.kshrd.wearekhmer.history.model.response.HistoryResponse;
import com.kshrd.wearekhmer.history.repository.HistoryMapper;
import com.kshrd.wearekhmer.notification.INotificationMapper;
import com.kshrd.wearekhmer.user.repository.AuthorRepository;
import com.kshrd.wearekhmer.user.repository.UserAppRepository;
import com.kshrd.wearekhmer.userArtivities.repository.ICommentRepository;
import com.kshrd.wearekhmer.userArtivities.repository.IReactRepository;
import com.kshrd.wearekhmer.userRating.RatingRepository;
import com.kshrd.wearekhmer.userReport.repository.ReportMapper;
import com.kshrd.wearekhmer.utils.WeAreKhmerConstant;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import com.kshrd.wearekhmer.utils.enumUtil.EGender;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

    private final ArticleMapper articleMapper;

    private final UserAppRepository userAppRepository;

    private final RatingRepository ratingRepository;

    private final IReactRepository reactRepository;

    private final HeroCardRepository heroCardRepository;

    private final INotificationMapper notificationMapper;

    private final ICommentRepository commentRepository;

    private final AuthorRepository authorRepository;

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
        }
        throw new ValidateException("Gender must be lowercase and be formatted in (male, female, other).", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());

    }


    private boolean validate(String password) {
        Pattern pattern = Pattern.compile(weAreKhmerConstant.PASSWORD_PATTERN);
        return pattern.matcher(password).matches();
    }


    public void passwordValidation(String password) {
        if (password.length() < 8) {
            throw new ValidateException("Password must be at least 8 characters", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
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
        if (!articleService.isArticleExist(articleId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article ID : " + articleId + " does not exists");
        }
        return articleService.isArticleExist(articleId);

    }

    @Override
    public boolean validateReportId(String reportId) {
        if (!reportMapper.isReportExistByIs(reportId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Report ID : " + reportId + " does not exists");
        return reportMapper.isReportExistByIs(reportId);
    }

    @Override
    public boolean validateHistoryId(String historyId, String userId) {
        if (!historyMapper.validateHistoryId(historyId, userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User have no History ID : " + historyId);
        }
        return historyMapper.validateHistoryId(historyId, userId);
    }

    @Override
    public List<HistoryResponse> validateHistoryRemoveAll(String userId) {
        return historyMapper.getAllHistoryByCurrentUser(userId);
    }

    @Override
    public boolean validateBookmarkId(String bookmarkId, String userId) {
        if (!bookmarkMapper.validateBookmarkId(bookmarkId, userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User have no Bookmark ID : " + bookmarkId);
        }
        return bookmarkMapper.validateBookmarkId(bookmarkId, userId);
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

    @Override
    public boolean validateCategoryId(String categoryId) {

        if (!categoryMapper.isCategoryExist(categoryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "categoryId " + categoryId + " does not exists");
        }
        return categoryMapper.isCategoryExist(categoryId);
    }

    @Override
    public boolean validateArticleIdByCurrentUser(String articleId, String userId) {

        if (!articleMapper.validateArticleIdByCurrentUser(articleId, userId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You are not own this article or this article is not exist.");

        return articleMapper.validateArticleIdByCurrentUser(articleId, userId);

    }

    @Override
    public boolean checkAuthorExist(String authorId) {
        if (!(ratingRepository.isExistAuthor(authorId)))
            throw new ValidateException("No author had been found with this #id=" + authorId , HttpStatus.NOT_FOUND, HttpStatus.BAD_REQUEST.value());
        else
            return ratingRepository.isExistAuthor(authorId);
    }

    @Override
    public boolean checkCategoryNameExist(String categoryName) {
        if (!articleMapper.isCategoryNameExists(categoryName))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "categoryName : " + categoryName + "does not exists");
        return articleMapper.isCategoryNameExists(categoryName);

    }

    @Override
    public void validateAdminIsRejectOrApprove(String status) {
        List<String> requestStatus = new ArrayList<>();
        boolean matchOne = false;
        requestStatus.add("APPROVED");
        requestStatus.add("REJECTED");
        for (String r : requestStatus) {
            if (status.equals(r)) {
                matchOne = true;
                break;
            }
        }
        if (!matchOne) {
            throw new IllegalArgumentException("Required value status must be either [APPROVED, REJECTED]");
        }

    }

    @Override
    public boolean checkArticleByCategoryId(String categoryId, String articleId) {
        if(!heroCardRepository.checkArticleIsExistInCategory(categoryId,articleId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "article does not exists in category");
        return heroCardRepository.checkArticleIsExistInCategory(categoryId,articleId);
    }

    @Override
    public boolean checkIndexIsExist(Integer index) {
        if(!heroCardRepository.checkIsIndexExist(index))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Index : "+index + "is already existed");
        return heroCardRepository.checkIsIndexExist(index);
    }

    @Override
    public boolean checkHeroCard(String categoryId, Integer index, String articleId) {
        if(heroCardRepository.checkHeroCardIsExist(categoryId,index,articleId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This article already exists in index : "+index);
        return true;
    }

    @Override
    public boolean validate3ArticleInCategoryId(String categoryId) {
        if(heroCardRepository.articleInHeroCard(categoryId))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "There's no index space, please delete one to insert");
        return true;
    }

    @Override
    public boolean checkIsCategoryIdOutOfIndex(String categoryId, Integer index) {
        return heroCardRepository.checkIsIndexOfCategoryIdFull(categoryId,index);
    }

//    @Override
//    public boolean checkHeroCardId(String heroCardId) {
//        return heroCardRepository.checkHeroCardIsExist(heroCardId);
//    }


    @Override
    public boolean validateNotificationId(String userId, String notificationId) {
        if(!notificationMapper.checkAuthorHasNotificationId(userId,notificationId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You don't have this notification");
        return true;
    }

    @Override
    public void validateNotificationTypeAdmin(String notificationType) {
        List<String> notification = new ArrayList<>();
        boolean matchOne = false;
        notification.add("REPORT_ON_ARTICLE");
        notification.add("USER_REQUEST_AS_AUTHOR");
        notification.add("USER_REPORT_AUTHOR");
        for (String type : notification) {
            if (notificationType.equals(type)) {
                matchOne = true;
                break;
            }
        }
        if (!matchOne) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Required value type must be either [REPORT_ON_ARTICLE, USER_REQUEST_AS_AUTHOR, USER_REPORT_AUTHOR]");
        }
    }

    @Override
    public boolean validateNotificationExistInType( String notificationId,String notificationType) {
        if(!notificationMapper.validateNotificationIdExistInNotificationType(notificationId,notificationType))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NotificationId does not exist in NotificationType");
        return notificationMapper.validateNotificationIdExistInNotificationType(notificationId,notificationType);
    }


    @Override
    public java.sql.Date validateDateOfBirth(String dateOfBirth) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        simpleDateFormat.setLenient(false);
        try {
            java.util.Date javaDate = simpleDateFormat.parse(dateOfBirth);
            java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
            return sqlDate;
        } catch (ParseException e) {
            throw new ValidateException("Invalid Date of birth format. It must be in the format 'yyyy-MM-dd'.", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
        }
    }



    @Override
    public void validateStatus(String status) {
        List<String> Status = new ArrayList<>();
        boolean matchOne = false;
        Status.add("PENDING");
        Status.add("REJECTED");
        for (String type : Status) {
            if (status.equals(type)) {
                matchOne = true;
                break;
            }
        }
        if (!matchOne) {
            throw new ValidateException("Required value status must be either [PENDING, REJECTED]", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value() );
        }
    }


    @Override
    public void validateEmail(String email) {
        boolean isMatched =  Pattern.compile("[a-z0-9]+@[a-z]+\\.[a-z]{2,3}").matcher(email).matches();
        if (!isMatched) {
            throw new ValidateException("Invalid email address", HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.value());
        }
    }


    @Override
    public void validateAuthorHasAuthorityToReplyComment(String commentId, String userId) {
        boolean isMathch = commentRepository.validateAuthorHasAuthorityToReplyComment(commentId, userId);
        if(!isMathch) {
            throw new ValidateException("This author has no authorities to reply this comment, he is not own of this article", HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.value());
        }
    }

    @Override
    public void validateReportType(String status) {
        List<String> input = new ArrayList<>();
        boolean matchOne = false;
        input.add("REPORT_ON_ARTICLE");
        input.add("USER_REPORT_AUTHOR");

        for(String check : input){
            if(status.equals(check)){
                matchOne = true;
                break;
            }
        }
        if(!matchOne){
            throw new ValidateException("Required value status must be either [REPORT_ON_ARTICLE, USER_REPORT_AUTHOR]", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value() );
        }
    }

    @Override
    public void validateHeroType(String type) {
        boolean isMatch = false;
        List<String> input = new ArrayList<>();
        input.add("category");
        input.add("home");

        for(String check : input){
            if(type.equals(check)){
                isMatch = true;
                break;
            }

        }
        if(!isMatch)
            throw new ValidateException("Required value status must be either [home, category]", HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value());

    }

    @Override
    public boolean validateHeroCardIndexExist(Integer index, String type, String categoryId) {
        if(heroCardRepository.validateHeroCardIndexExist(index,type,categoryId))
            throw new ValidateException("This index is not available", HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value());
        else
             return true;
    }

    @Override
    public boolean checkArticleAlreadyExistInHeroCard(String categoryId, String articleId, String type) {
        if(heroCardRepository.checkArticleAlreadyExistInHeroCard(categoryId,articleId,type))
            throw new ValidateException("This article already used to be hero card in "+type+ " of categoryId : "+categoryId, HttpStatus.FORBIDDEN,HttpStatus.FORBIDDEN.value());
        else
            return true;
    }

    @Override
    public boolean checkCategoryId(String categoryId) {
        if(categoryMapper.isCategoryExist(categoryId))
            return true;
        else
            throw new ValidateException("categoryId : "+categoryId+ " does not exist", HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
    }

    @Override
    public boolean checkEducationId(String educationId, String userId) {
        if(!authorRepository.checkEducationIdForCurrentAuthor(educationId,userId))
            throw new ValidateException("You don't have this educationId : "+educationId,HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
        return true;
    }

    @Override
    public boolean checkQuoteId(String quoteId, String userId) {
        if(!authorRepository.checkQuoteIdForCurrentAuthor(quoteId,userId))
            throw new ValidateException("You don't have this quoteId : "+quoteId,HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
        return true;
    }

    @Override
    public boolean checkWorkingExperienceId(String workingExperienceId, String userId) {
        if(!authorRepository.checkWorkingExperienceIdForCurrentAuthor(workingExperienceId,userId))
            throw new ValidateException("You don't have this workingExperienceId : "+workingExperienceId,HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
        return true;
    }
}




