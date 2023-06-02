package com.kshrd.wearekhmer.userArtivities.model.navbar;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class NavbarOrdering {
    //    Map<String, String> navbar;
    private List<Navbar> navbar;
}
