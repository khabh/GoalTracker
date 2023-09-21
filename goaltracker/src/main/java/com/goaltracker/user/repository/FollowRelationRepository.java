package com.goaltracker.user.repository;

import com.goaltracker.user.domain.FollowRelation;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.dto.vo.FollowStatsVO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRelationRepository extends JpaRepository<FollowRelation, Long> {

    boolean existsByFollowee_idAndFollower_id(Long followeeId, Long followerId);

    @Query("SELECT new com.goaltracker.user.dto.vo.FollowStatsVO(" +
            "(SELECT COUNT(followers.id) FROM FollowRelation followers WHERE followers.followee = :user), " +
            "(SELECT COUNT(followings.id) FROM FollowRelation followings WHERE followings.follower = :user))"
    )
    FollowStatsVO findFollowStatsByUser(@Param("user") User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM " +
            "FollowRelation followRelation " +
            "WHERE followRelation.followee.id = :followeeId AND followRelation.follower.id = :followerId")
    void deleteByFolloweeIdAndFollowerId(@Param("followeeId") Long followeeId, @Param("followerId") Long followerId);
}
