package com.kshrd.wearekhmer.userArtivities.service;


import com.kshrd.wearekhmer.userArtivities.model.navbar.Navbar;
import com.kshrd.wearekhmer.userArtivities.model.navbar.NavbarResponse;

import java.util.List;

public interface NavbarService {
    Navbar updateNavbar(Navbar navbar);
    List<Navbar> getNavbar();
    List<NavbarResponse> getNavbarAsNavbarResponse();
}
