package com.kshrd.wearekhmer.user.service;

import com.kshrd.wearekhmer.article.model.Response.BanAuthor;
import com.kshrd.wearekhmer.user.model.dto.AuthorDTO;
import com.kshrd.wearekhmer.user.model.entity.AuthorRequestTable;
import com.kshrd.wearekhmer.user.model.entity.UpdateAccountSetting;
import com.kshrd.wearekhmer.user.model.entity.UpdateProfile;
import com.kshrd.wearekhmer.userRating.reponse.PersonalInformationResponse;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

public interface AuthorService {
    List<AuthorDTO> getAllAuthor(Integer pageNumber, Integer nextPage);

    AuthorDTO getAllAuthorById(String authorId);

    List<AuthorRequestTable> getAll();

    String updateUserRequestToBeAsAuthor(String userId);

    String updateUserRequestToBeAsAuthorAsReject(String userId);

    AuthorDTO getCurrentAuthorById(String userId);



    PersonalInformationResponse getAuthorPersonalInfoByAuthorId(String authorId);

    UpdateAccountSetting updateAccountSetting(UpdateAccountSetting updateAccountSetting);

    UpdateProfile updateProfile(String userId, String photoUrl);
    BanAuthor adminBanAuthor(String userId);

    BanAuthor adminUnBanAuthor(String userId);

    List<BanAuthor> adminGetAllBannedAuthor(Integer pageNumber, Integer nextPage);
}
