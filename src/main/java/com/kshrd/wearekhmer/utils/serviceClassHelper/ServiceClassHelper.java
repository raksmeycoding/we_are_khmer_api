package com.kshrd.wearekhmer.utils.serviceClassHelper;

import com.kshrd.wearekhmer.user.model.entity.AuthorRequest;
import com.kshrd.wearekhmer.user.model.entity.AuthorRequestTable;

public interface ServiceClassHelper {
    AuthorRequestTable insertAndGetAuthorRequestFromDatabase(AuthorRequest authorRequest);




}