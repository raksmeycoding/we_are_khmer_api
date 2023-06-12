package com.kshrd.wearekhmer.notification.entity.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestAuthorList {
    private String authorRequestId;
    private String userId;
    private String authorRequestName;
    private String createAt;

    private String reason;
    private String email;
    private String photoUrl;

    private String isAccepted;

}
