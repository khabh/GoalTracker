package com.goaltracker.auth.service;

import com.goaltracker.auth.domain.Authority;
import com.goaltracker.auth.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Set<Authority> getOrCreateAuthorities(List<String> authorityNames) {
        return authorityNames.stream()
                .map(authorityName -> authorityRepository.save(new Authority(authorityName)))
                .collect(Collectors.toSet());
    }
}
