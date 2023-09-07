package com.goaltracker.user.service;

import com.goaltracker.user.domain.Interest;

import java.util.List;

public interface InterestService {
    List<Interest> getOrCreateInterestsByTagName(List<String> tagNames);
}
