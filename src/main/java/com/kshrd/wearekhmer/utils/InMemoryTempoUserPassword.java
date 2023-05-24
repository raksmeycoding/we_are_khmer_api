package com.kshrd.wearekhmer.utils;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@Component
public class InMemoryTempoUserPassword {
    List<UserTemPassword> userTemPasswords;

}
