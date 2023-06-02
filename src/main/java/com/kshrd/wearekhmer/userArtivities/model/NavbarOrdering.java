package com.kshrd.wearekhmer.userArtivities.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class NavbarOrdering {
    List<String> navbar;
}
