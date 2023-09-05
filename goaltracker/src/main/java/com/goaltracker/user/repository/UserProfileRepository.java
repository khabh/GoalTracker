package com.goaltracker.user.repository;

import com.goaltracker.user.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    UserProfile getUserProfileByUser_Username(String username);
}
