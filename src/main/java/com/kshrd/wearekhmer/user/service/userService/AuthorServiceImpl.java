package com.kshrd.wearekhmer.user.service.userService;

import com.kshrd.wearekhmer.exception.ValidateException;
import com.kshrd.wearekhmer.user.model.dto.AuthorDTO;
import com.kshrd.wearekhmer.user.model.entity.*;
import com.kshrd.wearekhmer.user.repository.AuthorRepository;
import com.kshrd.wearekhmer.user.repository.EducationMapper;
import com.kshrd.wearekhmer.user.repository.QuoteMapper;
import com.kshrd.wearekhmer.user.repository.WorkingExperienceMapper;
import com.kshrd.wearekhmer.user.service.AuthorRequestTableService;
import com.kshrd.wearekhmer.user.service.AuthorService;
import com.kshrd.wearekhmer.userRating.reponse.PersonalInformationResponse;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import com.kshrd.wearekhmer.utils.validation.DefaultWeAreKhmerValidation;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;


@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorRequestTableService, AuthorService {
    private final AuthorRepository authorRepository;

    private WeAreKhmerValidation weAreKhmerValidation;
    private final DefaultWeAreKhmerValidation defaultWeAreKhmerValidation;

    private WeAreKhmerCurrentUser weAreKhmerCurrentUser;

    private final WorkingExperienceMapper workingExperienceMapper;

    private final EducationMapper educationMapper;
    private final QuoteMapper quoteMapper;
    @Override
    public List<AuthorRequestTable> getAll() {
        return authorRepository.getAll();
    }

    @Override
    public List<AuthorRequestTable> getAllByUserId(String userId) {
        return null;
    }

    @Override
    public AuthorRequestTable getById(String authorRequestId) {
        return null;
    }

    @Override
    public AuthorRequestTable insert(AuthorRequestTable authorRequest) {
        return null;
    }

    @Override
    public AuthorRequestTable update(AuthorRequestTable authorRequest) {
        return null;
    }

    @Override
    public AuthorRequestTable delete(String authorRequestId) {
        return authorRepository.delete(authorRequestId);
    }

    @Override
    public List<AuthorDTO> getAllAuthor() {
        return authorRepository.getAllAuthor();
    }


    @Override
    public AuthorDTO getAllAuthorById(String authorId) {
        return authorRepository.getAllAuthorById(authorId);
    }

    @Override
    public String updateUserRequestToBeAsAuthor(String userId) {
        return authorRepository.updateUserRequestToBeAsAuthor(userId);
    }

    @Override
    public String updateUserRequestToBeAsAuthorAsReject(String userId) {
        return authorRepository.updateUserRequestToBeAsAuthorAsReject(userId);
    }

    @Override
    public AuthorDTO getCurrentAuthorById(String userId) {
        return authorRepository.getCurrentAuthorById(userId);
    }


    @Override
    public PersonalInformationResponse getAuthorPersonalInfoByAuthorId(String authorId) {
        return authorRepository.getAuthorPersonalInfoByAuthorId(authorId);
    }

    @Override
    public UpdateAccountSetting updateAccountSetting(UpdateAccountSetting updateAccountSetting) {
        String authorId = weAreKhmerCurrentUser.getUserId();
        weAreKhmerValidation.genderValidation(updateAccountSetting.getGender());
        java.sql.Date dateOfBirth = defaultWeAreKhmerValidation.validateDateOfBirth(updateAccountSetting.getDateOfBirth());

        List<WorkingExperienceResponse> workingExperience = updateAccountSetting.getWorkingExperiences();
        assert workingExperience != null;
        List<EducationResponse> education = updateAccountSetting.getEducations();
        assert education != null;
        List<QuoteResponse> bio = updateAccountSetting.getBio();
        assert bio != null;

        for(WorkingExperienceResponse  workingExperienceResponse : workingExperience){
            WorkingExperienceResponse workingExperience1 = WorkingExperienceResponse.builder()
                    .workingExperienceId(workingExperienceResponse.getWorkingExperienceId())
                    .workingExperienceName(workingExperienceResponse.getWorkingExperienceName())
                    .build();
            weAreKhmerValidation.checkWorkingExperienceId(workingExperienceResponse.getWorkingExperienceId(),authorId);
            WorkingExperienceResponse workingExperience2 = workingExperienceMapper.updateCurrentAuthorWorkingExperience(authorId,workingExperienceResponse.getWorkingExperienceId(),workingExperienceResponse.getWorkingExperienceName());
            System.out.println(workingExperience2);
        }

        for(EducationResponse educationResponse : education){
            EducationResponse education1 = EducationResponse.builder()
                    .educationId(educationResponse.getEducationId())
                    .educationName(educationResponse.getEducationName())
                    .build();
            weAreKhmerValidation.checkEducationId(educationResponse.getEducationId(),authorId);
            EducationResponse education2 = educationMapper.updateCurrentAuthorEducation(authorId,educationResponse.getEducationId(), educationResponse.getEducationName());
            System.out.println(education2);
        }

        for(QuoteResponse quoteResponse : bio){
            QuoteResponse quote1 = QuoteResponse.builder()
                    .quoteId(quoteResponse.getQuoteId())
                    .quoteName(quoteResponse.getQuoteName())
                    .build();
            weAreKhmerValidation.checkQuoteId(quoteResponse.getQuoteId(),authorId);
            QuoteResponse quote2 = quoteMapper.updateCurrentAuthorQuote(authorId,quoteResponse.getQuoteId(), quoteResponse.getQuoteName());
            System.out.println(quote2);
        }


        return authorRepository.updateAuthorAccountSetting(updateAccountSetting.getUsername(),updateAccountSetting.getGender(),authorId, new java.sql.Timestamp(dateOfBirth.getTime()));
    }

    @Override
    public UpdateProfile updateProfile(String userId, String photoUrl) {
        if(!authorRepository.checkUserIdExist(weAreKhmerCurrentUser.getUserId()))
            throw new ValidateException("There's no userId : "+userId, HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
        return authorRepository.updateProfile(photoUrl,userId);
    }
}
