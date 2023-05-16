package com.kshrd.wearekhmer.user.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Education {
    private String educationId;
    private String educationName;
    private String userId;
}
