package com.kshrd.wearekhmer.user.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;
import java.sql.Timestamp;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorRequestTable {
    private String authorRequestId;

    private String userId;
    private String authorRequestName;
    private String isAuthorAccepted;
    private Timestamp createdAt;
    private String reason;
}
