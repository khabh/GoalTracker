package com.goaltracker.user.repository;

import com.goaltracker.user.domain.Interest;
import com.goaltracker.user.domain.UserProfile;
import com.goaltracker.user.domain.UserProfileInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserProfileInterestRepository extends JpaRepository<UserProfileInterest, Long> {
    @Query("SELECT userProfileInterest.interest " +
        "FROM UserProfileInterest userProfileInterest " +
        "WHERE userProfileInterest.userProfile = :userProfile")
    Set<Interest> getInterestsByUserProfile(@Param("userProfile") UserProfile userProfile);

    void deleteByUserProfileAndInterestIn(UserProfile userProfile, List<Interest> interests);

    void deleteByUserProfile(UserProfile userProfile);
}
