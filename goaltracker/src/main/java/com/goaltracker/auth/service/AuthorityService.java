package com.goaltracker.auth.service;

import com.goaltracker.auth.domain.Authority;

import java.util.List;
import java.util.Set;

public interface AuthorityService {
    Set<Authority> getOrCreateAuthorities(List<String> authorityNames);
}
