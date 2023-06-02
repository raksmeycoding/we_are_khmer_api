package com.kshrd.wearekhmer.userArtivities.service;

import com.kshrd.wearekhmer.userArtivities.model.navbar.Navbar;
import com.kshrd.wearekhmer.userArtivities.repository.NavbarMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NavbarServiceImpl implements NavbarService {
    private final NavbarMapper navbarMapper;
    @Override
    public Navbar updateNavbar(Navbar navbar) {
        return navbarMapper.updateNavbar(navbar);
    }


    @Override
    public List<Navbar> getNavbar() {
        return navbarMapper.getNavbar();
    }
}
