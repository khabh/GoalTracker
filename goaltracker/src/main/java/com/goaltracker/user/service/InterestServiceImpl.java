package com.goaltracker.user.service;

import com.goaltracker.user.domain.Interest;
import com.goaltracker.user.repository.InterestRepository;
import com.goaltracker.user.util.InterestConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InterestServiceImpl implements InterestService {

    private final InterestRepository interestRepository;

    @Autowired
    public InterestServiceImpl(InterestRepository interestRepository) {
        this.interestRepository = interestRepository;
    }

    @Override
    public List<Interest> getOrCreateInterestsByTagName(List<String> tagNames) {
        List<Interest> interests = interestRepository.findByTagNameIn(tagNames);
        Set<String> existingTagNames = interests.stream()
                .map(Interest::getTagName)
                .collect(Collectors.toSet());

        List<Interest> newInterests = createAndSaveNotExistingInterests(tagNames, existingTagNames);
        interests.addAll(newInterests);

        return interests;
    }

    private List<Interest> createAndSaveNotExistingInterests(List<String> tagNames, Set<String> existingTagNames) {
        List<Interest> newInterests = tagNames.stream()
                .filter(tagName -> !existingTagNames.contains(tagName))
                .map(InterestConverter::toInterest)
                .collect(Collectors.toList());
        interestRepository.saveAll(newInterests);

        return newInterests;
    }
}
