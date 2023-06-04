package com.kshrd.wearekhmer.userArtivities.model.navbar;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class NavbarResponse {
    private String navbar_id;
    private String category_id;
    private String real_name;
    private String navbar_name;
    private Integer order_number;
}
