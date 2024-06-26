package com.kshrd.wearekhmer.utils.serviceClassHelper;


import com.kshrd.wearekhmer.article.repository.ArticleMapper;
import com.kshrd.wearekhmer.exception.CustomRuntimeException;
import com.kshrd.wearekhmer.files.config.FileConfig;
import com.kshrd.wearekhmer.repository.WeAreKhmerRepositorySupport;
import com.kshrd.wearekhmer.user.model.entity.*;
import com.kshrd.wearekhmer.user.repository.AuthorRepository;
import com.kshrd.wearekhmer.user.repository.EducationMapper;
import com.kshrd.wearekhmer.user.repository.QuoteMapper;
import com.kshrd.wearekhmer.user.repository.WorkingExperienceMapper;
import com.kshrd.wearekhmer.utils.WeAreKhmerConstant;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

@Data
@AllArgsConstructor
@Service
public class ServiceHelperImpl implements ServiceClassHelper {
    private final WeAreKhmerCurrentUser weAreKhmerCurrentUser;
    private final EducationMapper educationMapper;
    private final QuoteMapper quoteMapper;

    private final WorkingExperienceMapper workingExperienceMapper;

    private final AuthorRepository authorRepository;

    private final WeAreKhmerConstant weAreKhmerConstant;

    private final WeAreKhmerRepositorySupport weAreKhmerRepositorySupport;

    private final ArticleMapper articleMapper;

    private final WeAreKhmerValidation weAreKhmerValidation;


    @Override
    public AuthorRequestTable insertAndGetAuthorRequestFromDatabase(AuthorRequest authorRequest) {
        String currentUserId = weAreKhmerCurrentUser.getUserId();

        weAreKhmerValidation.genderValidation(authorRequest.getGender());

        workingExperienceMapper.deleteAllWorkingExperienceIfUserIdExist(currentUserId);
        quoteMapper.deleteAllQuoteIfUserIdIsExist(currentUserId);
        educationMapper.deleteAllEducationIfUserIdExist(currentUserId);


        List<String> educations = authorRequest.getEducation();
        assert educations != null;
        List<String> workingExperiences = authorRequest.getWorkingExperience();
        assert workingExperiences != null;
        List<String> quotes = authorRequest.getQuote();
        assert quotes != null;


        if(educations.isEmpty()) {
            educations.add("None");
            educations.add("None");
            educations.add("None");
        } else if (educations.size() < 3) {

            int remainingSlots = 3 - educations.size();
            for (int i = 0; i < remainingSlots; i++) {
                educations.add("None");
            }
        }

        for (String education : educations) {
            Education education1 = Education.builder()
                    .educationName(education)
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .build();
            Education education11 = educationMapper.insert(education1);
        }


        if (quotes.isEmpty()) {
            quotes.add("None");
            quotes.add("None");
            quotes.add("None");
        } else if (quotes.size() < 3) {

            int remainingSlots = 3 - quotes.size();
            for (int i = 0; i < remainingSlots; i++) {
                quotes.add("None");
            }
        }

        for (String quote : quotes) {
            Quote quoteName = Quote.builder()
                    .quoteName(quote)
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .build();
            Quote education11 = quoteMapper.insert(quoteName);
        }

        if (workingExperiences.isEmpty()) {
            workingExperiences.add("None");
            workingExperiences.add("None");
            workingExperiences.add("None");
        } else if (workingExperiences.size() < 3) {

            int remainingSlots = 3 - quotes.size();
            for (int i = 0; i < remainingSlots; i++) {
                workingExperiences.add("None");
            }
        }

        for (String workingExperience : quotes) {
            WorkingExperience workingExperience1 = WorkingExperience.builder()
                    .workingExperienceName(workingExperience)
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .build();
            WorkingExperience workingExperience2 = workingExperienceMapper.insert(workingExperience1);
        }


        AuthorRequestTable authorRequestTable =
                AuthorRequestTable.builder()
                        .authorRequestName(authorRequest.getAuthorName())
                        .userId(weAreKhmerCurrentUser.getUserId())
                        .reason(authorRequest.getReason())
                        .build();

        return authorRepository.insert(authorRequestTable);
    }


    @Override
    public String uploadImageToSpecificTable(String imageType, String imageName, String primaryId) {
        if (imageType.equalsIgnoreCase("CATEGORY")) {
            return weAreKhmerRepositorySupport.uploadImageToCategoryTb(imageName, primaryId);
        } else if (imageType.equalsIgnoreCase("USER")) {
            return weAreKhmerRepositorySupport.uploadImageToUserTb(imageName, primaryId);
        } else if (imageType.equalsIgnoreCase("ARTICLE")) {
            return weAreKhmerRepositorySupport.uploadImageToArticleTb(imageName, primaryId);
        }
        throw new CustomRuntimeException("Incorrect part variable. (We only accept category, user, article.)");
    }




    @Override
    public Integer getTotalOfRecordInArticleTb() {
        return articleMapper.getTotalRecordOfArticleTb();
    }




    @Override
    public Integer getTotalOfRecordInArticleTbForCurrentUser() {
        return articleMapper.getTotalRecordOfArticleForCurrentUser(weAreKhmerCurrentUser.getUserId());
    }

    public static class FileDeletion {
        private final ResourceLoader resourceLoader;


        private final String[] imagePath = {"static",  "images"};

        public FileDeletion() {
            this.resourceLoader = new DefaultResourceLoader();
        }


        public void deleteFileByName(String fileName) throws IOException {
            String filePath = String.join("/", imagePath) + "/" + fileName;
            Resource resource = resourceLoader.getResource("classpath:" + filePath);
            File file = resource.getFile();
            System.out.println(fileName);
            Path path = file.toPath();
            Files.delete(path);
            if (Files.exists(path)) {
                System.out.println("Unable to delete file!");
            } else {
                System.out.println("File deleted!");
            }
        }



        public static FileDeletion getInstance() {
            return new FileDeletion();
        }
    }


//    public void validatePSQLException(HttpServletRequest httpServletRequest, Exception  ex){
//        if (ex.getCause() instanceof SQLException) {
//            String message = ex.getCause().getMessage();
//            System.out.println(message);
//            int startIndex = message.indexOf("ERROR:");
//            int endIndex = message.indexOf("\n");
//            String returnMessage = message.substring(startIndex, endIndex);
//            System.out.println(returnMessage);            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, returnMessage);
//            URI uri = URI.create(httpServletRequest.getRequestURL().toString());
//            problemDetail.setType(uri);
//            throw new ErrorResponseException(HttpStatus.FORBIDDEN, problemDetail, ex.getCause());
//        }
//    }


    @Override
    public void helpThrowPSQLException(HttpServletRequest httpServletRequest, Exception ex) {
        if (ex.getCause() instanceof SQLException) {
            String message = ex.getCause().getMessage();
            System.out.println(message);
            int startIndex = message.indexOf("ERROR:");
            int endIndex = message.indexOf("\n");
            String returnMessage = message.substring(startIndex, endIndex);
            System.out.println(returnMessage);            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, returnMessage);
            URI uri = URI.create(httpServletRequest.getRequestURL().toString());
            problemDetail.setType(uri);
            throw new ErrorResponseException(HttpStatus.FORBIDDEN, problemDetail, ex.getCause());
        }
    }
}
