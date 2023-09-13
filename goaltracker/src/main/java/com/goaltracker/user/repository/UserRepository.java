package com.goaltracker.user.repository;

import com.goaltracker.user.domain.User;
import com.goaltracker.user.dto.vo.FollowStatsVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT u.email " +
            "FROM User u " +
            "WHERE u.username = :username")
    Optional<String> findEmailByUsername(@Param("username") String username);

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("SELECT user.id " +
            "FROM User  user " +
            "WHERE user.username = :username"
    )
    Optional<Long> findUserIdByUsername(@Param("username") String username);

    @Query("SELECT new com.goaltracker.user.dto.vo.FollowStatsVO(" +
            "(SELECT COUNT(followers.id) FROM FollowRelation followers WHERE followers.followee = :user), " +
            "(SELECT COUNT(followings.id) FROM FollowRelation followings WHERE followings.follower = :user))"
    )
    FollowStatsVO findFollowStatsByUser(@Param("user") User user);
}
