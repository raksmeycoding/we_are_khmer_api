package com.kshrd.wearekhmer.requestRequest;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserRepsonse {
    private String token;
}
