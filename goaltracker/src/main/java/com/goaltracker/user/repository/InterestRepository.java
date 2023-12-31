package com.goaltracker.user.repository;

import com.goaltracker.user.domain.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {
    List<Interest> findByTagNameIn(List<String> tagNames);
}
