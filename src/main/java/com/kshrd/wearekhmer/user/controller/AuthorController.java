package com.kshrd.wearekhmer.user.controller;


import com.kshrd.wearekhmer.emailVerification.service.EmailService;
import com.kshrd.wearekhmer.exception.ValidateException;
import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.user.model.dto.AuthorDTO;
import com.kshrd.wearekhmer.user.model.entity.*;
import com.kshrd.wearekhmer.user.repository.AuthorRepository;
import com.kshrd.wearekhmer.user.repository.EducationMapper;
import com.kshrd.wearekhmer.user.repository.QuoteMapper;
import com.kshrd.wearekhmer.user.repository.WorkingExperienceMapper;
import com.kshrd.wearekhmer.user.service.AuthorRequestTableService;
import com.kshrd.wearekhmer.user.service.AuthorService;
import com.kshrd.wearekhmer.user.service.userService.AuthorServiceImpl;
import com.kshrd.wearekhmer.userRating.reponse.PersonalInformationResponse;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import com.kshrd.wearekhmer.utils.validation.DefaultWeAreKhmerValidation;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RequestMapping("/api/v1/author")
@SecurityRequirement(name = "bearerAuth")
@RestController
@AllArgsConstructor
public class AuthorController {


    private final AuthorRequestTableService authorRequestTableService;
    private final AuthorService authorService;

    private final AuthorRepository authorRepository;

    private final WeAreKhmerValidation weAreKhmerValidation;

    private WeAreKhmerCurrentUser weAreKhmerCurrentUser;

    private final DefaultWeAreKhmerValidation defaultWeAreKhmerValidation;

    private final AuthorServiceImpl authorServiceImpl;

    private final EmailService emailService;


    @GetMapping("/authorRequest")
    @Operation(summary = "(Get all authors request either accept as author or not.)")
    public ResponseEntity<?> getAllUserRequestAsAuthorEitherAcceptOrNot() {
        try {
            List<AuthorRequestTable> authorRequestTable = authorRequestTableService.getAll();
            return ResponseEntity.ok(authorRequestTable);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        }
    }


    @GetMapping("/authorUser")
    @Operation(summary = "(Get all only authors user.)")
    public ResponseEntity<?> getAllAuthorUser() {
        try {
            List<AuthorDTO> authorDTOList = authorService.getAllAuthor();
            return ResponseEntity.ok(authorDTOList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }


    @GetMapping("/{authorId}")
    @Operation(summary = "(User view author profile)")
    public ResponseEntity<?> getAllAuthorById(HttpServletRequest httpServletRequest, @PathVariable String authorId) {
        AuthorDTO authorDTO = authorService.getAllAuthorById(authorId);
        if (authorDTO == null) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Author had been not found!");
            problemDetail.setType(URI.create(httpServletRequest.getRequestURL().toString()));
            throw new ErrorResponseException(HttpStatus.NOT_FOUND, problemDetail, null);
        }
        return ResponseEntity.ok().body(GenericResponse.builder()
                .status("200")
                .title("success")
                .message("Get author profile successfully.")
                .payload(authorDTO)
                .build());
    }


    @PostMapping("accept/{userId}")
    @Operation(summary = "(Accept user request as author.)")
    public ResponseEntity<?> updateUserRequestToBeAsAuthor(@PathVariable String userId) throws MessagingException {
        String hasRoleAuthor = authorRepository.userAlreadyAuthor(userId);
        if (hasRoleAuthor != null && hasRoleAuthor.equalsIgnoreCase("ROLE_AUTHOR")) {
            throw new ValidateException("User had already be author.", HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.value());
        }

        GetEmailAndNameUser getEmailAndNameUser = authorRepository.getEmailAndName(userId);

        emailService.sendEmailToAuthor(getEmailAndNameUser.getEmail(), getEmailAndNameUser.getUserName());
        String userIdAccepted = authorService.updateUserRequestToBeAsAuthor(userId);


        GenericResponse res;
        if (userIdAccepted == null) {
            res = GenericResponse.builder()
                    .status("500")
                    .message("is not accepted.")
                    .build();
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        res = GenericResponse.builder()
                .statusCode(200)
                .message("Successfully accepted as author.")
                .title("success")
                .build();
        return ResponseEntity.ok(res);

    }

    @PostMapping("rejected/{userId}")
    @Operation(summary = "(Accept user request as author.)")
    public ResponseEntity<?> updateUserRequestToBeAsRejected(@PathVariable String userId) {
        String hasRoleAuthor = authorRepository.userAlreadyAuthor(userId);
//        check author had been already rejected or banded
        boolean isAlreadyRejected = authorRepository.checkAuthorRequestHadBendedOrRejected(userId);
        if (isAlreadyRejected) {
            throw new ValidateException("This author had been already rejected. ⚠️ If you want to reject this author, we have a solution you can delete this author or ban to let this author can no longer post any article in your application", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
        }
//        check author had already author
        if (hasRoleAuthor != null && hasRoleAuthor.equalsIgnoreCase("ROLE_AUTHOR")) {
            throw new ValidateException("User had already be author.", HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.value());
        }
        String userIdAccepted = authorService.updateUserRequestToBeAsAuthorAsReject(userId);
        GenericResponse res;

        res = GenericResponse.builder()
                .statusCode(200)
                .message("Successfully rejected as author.")
                .title("success")
                .build();
        return ResponseEntity.ok(res);

    }

    @GetMapping("/profile")
    @Operation(summary = "(Author view own profile ( only Author) )")
    public ResponseEntity<?> getCurrentAuthor() {
        weAreKhmerValidation.checkAuthorExist(weAreKhmerCurrentUser.getUserId());
        AuthorDTO authorDTO = authorService.getCurrentAuthorById(weAreKhmerCurrentUser.getUserId());

        return ResponseEntity.ok().body(GenericResponse.builder()
                .status("200")
                .title("success")
                .message("Get author profile successfully.")
                .payload(authorDTO)
                .build());
    }


    @GetMapping("/personal-info/{authorId}")
    public ResponseEntity<?> getAuthorPeronalInfoByAuthorId(@PathVariable String authorId) {
        weAreKhmerValidation.checkAuthorExist(authorId);
        PersonalInformationResponse personalInformationResponse = authorRepository.getAuthorPersonalInfoByAuthorId(authorId);
        return ResponseEntity.ok().body(GenericResponse.builder()
                .title("success")
                .message("Get author profile successfully")
                .statusCode(200)
                .payload(personalInformationResponse)
                .build());
    }

    @GetMapping("/account-setting")
    @Operation(summary = "Get account setting for current author")
    public ResponseEntity<?> getAccountSettingByAuthorId(){
        String authorId =  weAreKhmerCurrentUser.getUserId();
        AccountSettingResponse accountSettingResponse = authorRepository.getAccountSetting(authorId);
        GenericResponse genericResponse = GenericResponse.builder()
                .statusCode(200)
                .title("success")
                .message("You have successfully gotten account setting of this authorId : "+authorId)
                .payload(accountSettingResponse)
                .build();
        return ResponseEntity.ok(genericResponse);
    }

    @PutMapping("/update-account-setting")
    @Operation(summary = "Update account setting for current author")
    public ResponseEntity<?> updateAccountSettingByAuthorId(@RequestBody @Validated UpdateAccountSetting updateAccountSetting){
        String authorId = weAreKhmerCurrentUser.getUserId();
        weAreKhmerValidation.genderValidation(updateAccountSetting.getGender());
        java.sql.Date dateOfBirth = defaultWeAreKhmerValidation.validateDateOfBirth(updateAccountSetting.getDateOfBirth());
        if (updateAccountSetting.getWorkingExperiences().size()>3) {
            throw new ValidateException("Working Experience has 3 maximum fields", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
        }
        if (updateAccountSetting.getEducations().size() > 3) {
            throw new ValidateException("Education has 3 maximum fields", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
        }
        if (updateAccountSetting.getBio().size() > 3) {
            throw new ValidateException("Bio has 3 maximum fields", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
        }

        UpdateAccountSetting updateAccountSetting1 = authorServiceImpl.updateAccountSetting(updateAccountSetting);

        GenericResponse genericResponse = GenericResponse.builder()
                .statusCode(200)
                .title("success")
                .message("You have successfully update your account setting")
                .payload(updateAccountSetting1)
                .build();
        return ResponseEntity.ok(genericResponse);

    }

    @PutMapping("/update-user-image")
    @Operation(summary = "Update user image for all user in platform")
    public ResponseEntity<?> updateProfile(String imageUrl){


        UpdateProfile updateProfile = authorRepository.updateProfile(imageUrl, weAreKhmerCurrentUser.getUserId());

        GenericResponse genericResponse = GenericResponse.builder()
                .statusCode(200)
                .title("success")
                .message("You have updated profile successfully")
                .payload(updateProfile)
                .build();

        return ResponseEntity.ok(genericResponse);

    }

}
