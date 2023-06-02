package com.kshrd.wearekhmer.userArtivities.service;


import com.kshrd.wearekhmer.userArtivities.model.navbar.Navbar;

import java.util.List;

public interface NavbarService {
    Navbar updateNavbar(Navbar navbar);
    List<Navbar> getNavbar();
}
