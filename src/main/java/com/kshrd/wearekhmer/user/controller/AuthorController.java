package com.kshrd.wearekhmer.user.controller;


import com.kshrd.wearekhmer.article.model.Response.BanAuthor;
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
import com.kshrd.wearekhmer.utils.serviceClassHelper.ServiceClassHelper;
import com.kshrd.wearekhmer.utils.validation.DefaultWeAreKhmerValidation;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import io.swagger.models.auth.In;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private final ServiceClassHelper serviceClassHelper;

    private static final Integer PAGE_SIZE = 10;
    private final EmailService emailService;

    private Integer getNextPage(Integer page) {
        int numberOfRecord = serviceClassHelper.getTotalOfRecordInArticleTb();
        System.out.println(numberOfRecord);
        int totalPage = (int) Math.ceil((double) numberOfRecord / PAGE_SIZE);
        System.out.println(totalPage);
        if (page > totalPage) {
            page = totalPage;
        }
        weAreKhmerValidation.validatePageNumber(page);
        return (page - 1) * PAGE_SIZE;
    }


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
    public ResponseEntity<?> getAllAuthorUser(@RequestParam(defaultValue = "1", required = false) Integer page) {
        Integer totalAuthor = authorRepository.totalAuthor();
        try {
            Integer nextPage = getNextPage(page);
            List<AuthorDTO> authorDTOList = authorService.getAllAuthor(PAGE_SIZE,nextPage);

            if(authorDTOList.size()>0){

                return ResponseEntity.ok(GenericResponse.builder()
                        .statusCode(200)
                        .totalAuthors(totalAuthor)
                        .message("You have successfully got total author")
                        .title("success")
                        .payload(authorDTOList)
                        .build());
            }

            return ResponseEntity.ok(GenericResponse.builder()
                    .statusCode(404)
                    .totalAuthors(totalAuthor)
                    .message("There's no authors in platform")
                    .title("failure")
                    .build());

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
        if(!authorRepository.checkUserId(userId))
            throw new ValidateException("User not found",HttpStatus.NOT_FOUND,HttpStatus.NOT_FOUND.value());
        String userIdAccepted = authorService.updateUserRequestToBeAsAuthor(userId);
        GetEmailAndNameUser getEmailAndNameUser = authorRepository.getEmailAndName(userId);

        emailService.sendEmailToAuthor(getEmailAndNameUser.getEmail(), getEmailAndNameUser.getUserName());


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
    @Operation(summary = "(Reject user request as author.)")
    public ResponseEntity<?> updateUserRequestToBeAsRejected(@PathVariable String userId) throws MessagingException {
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

        if(!authorRepository.checkUserId(userId))
            throw new ValidateException("User not found",HttpStatus.NOT_FOUND,HttpStatus.NOT_FOUND.value());
        String userIdAccepted = authorService.updateUserRequestToBeAsAuthorAsReject(userId);

        GetEmailAndNameUser getEmailAndNameUser = authorRepository.getEmailAndName(userId);

        emailService.rejectEmailToAuthor(getEmailAndNameUser.getEmail(), getEmailAndNameUser.getUserName());
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


    @GetMapping("/personal-info/{userId}")
    @Operation(summary = "View profile user (everyone)")
    public ResponseEntity<?> getAuthorPeronalInfoByAuthorId(@PathVariable String userId) {

        if(!authorRepository.checkUserId(userId))
            throw new ValidateException("User not found !", HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());

        PersonalInformationResponse personalInformationResponse = authorRepository.getAuthorPersonalInfoByAuthorId(userId);
        return ResponseEntity.ok().body(GenericResponse.builder()
                .title("success")
                .message("Get user profile successfully")
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

    @PutMapping("/admin/banAuthor")
    @Operation(summary = "Ban author for (only admin has permission to ban)")
    public ResponseEntity<?> banAuthor(@RequestParam("authorId") String authorId){

        weAreKhmerValidation.checkAuthorExist(authorId);

        if(authorRepository.checkAuthorIsBan(authorId))
            throw new ValidateException("This author already banned", HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.value());


        BanAuthor isAuthor = authorServiceImpl.adminBanAuthor(authorId);

        GenericResponse genericResponse = GenericResponse.builder()
                .message("You have successfully banned this author")
                .title("success")
                .payload(isAuthor)
                .statusCode(200)
                .build();

        return ResponseEntity.ok(genericResponse);
    }

    @PutMapping("/admin/unBanAuthor")
    @Operation(summary = "Unban author for (only admin has permission to unban)")
    public ResponseEntity<?> unBanAuthor(@RequestParam("authorId") String authorId){

        weAreKhmerValidation.checkAuthorExist(authorId);

        if(!authorRepository.checkAuthorIsBan(authorId))
            throw new ValidateException("This author has not banned", HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.value());


        BanAuthor isAuthor = authorServiceImpl.adminUnBanAuthor(authorId);

        GenericResponse genericResponse = GenericResponse.builder()
                .message("You have successfully unbanned this author")
                .title("success")
                .payload(isAuthor)
                .statusCode(200)
                .build();

        return ResponseEntity.ok(genericResponse);
    }
    @GetMapping("/admin/getAllBannedAuthor")
    @Operation(summary = "Get all banned author for (only admin has permission to get all banned author)")
    public ResponseEntity<?> getAllBannedAuthor(@RequestParam(defaultValue = "1", required = false) Integer page){
        GenericResponse genericResponse;
        Integer nextPage = getNextPage(page);
        Integer totalBannedAuthor = authorRepository.totalBannedAuthor();
        List<BanAuthor> getBanAuthor = authorServiceImpl.adminGetAllBannedAuthor(PAGE_SIZE,nextPage);

        if(getBanAuthor.size()>0){
           genericResponse = GenericResponse.builder()
                    .message("You have successfully gotten all banned author")
                    .totalAuthors(totalBannedAuthor)
                    .title("success")
                    .payload(getBanAuthor)
                    .statusCode(200)
                    .build();

            return ResponseEntity.ok(genericResponse);
        }

         genericResponse = GenericResponse.builder()
                .message("There's no banned author")
                .title("failure")
                .statusCode(404)
                .build();
        return ResponseEntity.ok(genericResponse);

    }

}
