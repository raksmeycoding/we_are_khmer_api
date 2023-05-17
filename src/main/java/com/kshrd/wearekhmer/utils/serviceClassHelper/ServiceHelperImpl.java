package com.kshrd.wearekhmer.utils.serviceClassHelper;


import com.kshrd.wearekhmer.security.WeAreKhmerSecurity;
import com.kshrd.wearekhmer.user.model.entity.*;
import com.kshrd.wearekhmer.user.repository.AuthorRepository;
import com.kshrd.wearekhmer.user.repository.EducationMapper;
import com.kshrd.wearekhmer.user.repository.QuoteMapper;
import com.kshrd.wearekhmer.user.repository.WorkingExperienceMapper;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@AllArgsConstructor
@Service
public class ServiceHelperImpl implements ServiceClassHelper{
    private final WeAreKhmerCurrentUser weAreKhmerCurrentUser;
    private final EducationMapper educationMapper;
    private final QuoteMapper quoteMapper;

    private final WorkingExperienceMapper workingExperienceMapper;

    private final AuthorRepository authorRepository;

    @Override
    public AuthorRequestTable insertAndGetAuthorRequestFromDatabase(AuthorRequest authorRequest) {
        String currentUserId = weAreKhmerCurrentUser.getUserId();

        List<String> educations = authorRequest.getEducation();
        assert educations != null;
        List<String> workingExperiences = authorRequest.getWorkingExperience();
        assert workingExperiences != null;
        List<String> quotes = authorRequest.getQuote();
        assert quotes != null;

        for (String education: educations) {
            Education education1 = Education.builder()
                    .educationName(education)
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .build();
            Education education11 = educationMapper.insert(education1);
        }

        for (String quote: quotes) {
            Quote toInsertQuote = Quote.builder()
                    .quoteName(quote)
                    .userId(weAreKhmerCurrentUser.getUserId())
                    .build();

            Quote insertedQuote = quoteMapper.insert(toInsertQuote);
        }

        for (String workingExperience: workingExperiences) {
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
}
