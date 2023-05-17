package com.kshrd.wearekhmer.user.service.userService;

import com.kshrd.wearekhmer.user.model.dto.AuthorDTO;
import com.kshrd.wearekhmer.user.model.entity.AuthorRequestTable;
import com.kshrd.wearekhmer.user.repository.AuthorRepository;
import com.kshrd.wearekhmer.user.service.AuthorRequestTableService;
import com.kshrd.wearekhmer.user.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorRequestTableService, AuthorService {
    private final AuthorRepository authorRepository;
    @Override
    public List<AuthorRequestTable> getAll() {
        return authorRepository.getAll();
    }

    @Override
    public List<AuthorRequestTable> getAllByUserId(String userId) {
        return null;
    }

    @Override
    public AuthorRequestTable getById(String authorRequestId) {
        return null;
    }

    @Override
    public AuthorRequestTable insert(AuthorRequestTable authorRequest) {
        return null;
    }

    @Override
    public AuthorRequestTable update(AuthorRequestTable authorRequest) {
        return null;
    }

    @Override
    public AuthorRequestTable delete(String authorRequestId) {
        return null;
    }

    @Override
    public List<AuthorDTO> getAllAuthor() {
        return authorRepository.getAllAuthor();
    }
}
