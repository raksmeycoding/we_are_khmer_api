package com.kshrd.wearekhmer.utils.serviceClassHelper;


import com.kshrd.wearekhmer.article.repository.ArticleMapper;
import com.kshrd.wearekhmer.exception.CustomRuntimeException;
import com.kshrd.wearekhmer.repository.WeAreKhmerRepositorySupport;
import com.kshrd.wearekhmer.user.model.entity.*;
import com.kshrd.wearekhmer.user.repository.AuthorRepository;
import com.kshrd.wearekhmer.user.repository.EducationMapper;
import com.kshrd.wearekhmer.user.repository.QuoteMapper;
import com.kshrd.wearekhmer.user.repository.WorkingExperienceMapper;
import com.kshrd.wearekhmer.utils.WeAreKhmerConstant;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

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


    @Override
    public AuthorRequestTable insertAndGetAuthorRequestFromDatabase(AuthorRequest authorRequest) {
        String currentUserId = weAreKhmerCurrentUser.getUserId();

        List<String> educations = authorRequest.getEducation();
        assert educations != null;
        List<String> workingExperiences = authorRequest.getWorkingExperience();
        assert workingExperiences != null;
        List<String> quotes = authorRequest.getQuote();
        assert quotes != null;

        for (String education : educations) {
            Education education1 = Education.builder()
                    .educationName(education)
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .build();
            Education education11 = educationMapper.insert(education1);
        }

        for (String quote : quotes) {
            Quote toInsertQuote = Quote.builder()
                    .quoteName(quote)
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .build();

            Quote insertedQuote = quoteMapper.insert(toInsertQuote);
        }

        for (String workingExperience : workingExperiences) {
            WorkingExperience toInsertWorkingExperience = WorkingExperience.builder()
                    .workingExperienceName(workingExperience)
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .build();

            WorkingExperience workingExperience1 = workingExperienceMapper.insert(toInsertWorkingExperience);
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
}
