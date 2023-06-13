package com.kshrd.wearekhmer.user.service;

import com.kshrd.wearekhmer.user.model.dto.AuthorDTO;
import com.kshrd.wearekhmer.user.model.entity.AuthorRequestTable;
import com.kshrd.wearekhmer.userRating.reponse.PersonalInformationResponse;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AuthorService {
    List<AuthorDTO> getAllAuthor();

    AuthorDTO getAllAuthorById(String authorId);

    List<AuthorRequestTable> getAll();

    String updateUserRequestToBeAsAuthor(String userId);

    String updateUserRequestToBeAsAuthorAsReject(String userId);

    AuthorDTO getCurrentAuthorById(String userId);



    PersonalInformationResponse getAuthorPersonalInfoByAuthorId(String authorId);
}
