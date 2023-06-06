package com.kshrd.wearekhmer.utils.serviceClassHelper;

import com.kshrd.wearekhmer.user.model.entity.AuthorRequest;
import com.kshrd.wearekhmer.user.model.entity.AuthorRequestTable;
import jakarta.servlet.http.HttpServletRequest;

public interface ServiceClassHelper {
    AuthorRequestTable insertAndGetAuthorRequestFromDatabase(AuthorRequest authorRequest);

    String uploadImageToSpecificTable(String imageType, String imageName, String primaryId);

    public Integer getTotalOfRecordInArticleTb();

    public Integer getTotalOfRecordInArticleTbForCurrentUser();


    public void helpThrowPSQLException(HttpServletRequest httpServletRequest, Exception ex);


}
