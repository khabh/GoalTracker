package com.goaltracker.user.service;

import com.goaltracker.user.domain.FollowRelation;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.dto.vo.FollowStatsVO;
import com.goaltracker.user.exception.follow.AttemptedSelfFollowException;
import com.goaltracker.user.exception.follow.FollowRelationDuplicatedException;
import com.goaltracker.user.exception.follow.FollowRelationNotFoundException;
import com.goaltracker.user.repository.FollowRelationRepository;
import com.goaltracker.user.util.FollowRelationConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class FollowRelationServiceImpl implements FollowRelationService {

    private final FollowRelationRepository followRelationRepository;

    @Autowired
    public FollowRelationServiceImpl(FollowRelationRepository followRelationRepository) {
        this.followRelationRepository = followRelationRepository;
    }

    @Override
    public FollowStatsVO getUserFollowStats(User user) {
        return followRelationRepository.findFollowStatsByUser(user);
    }

    @Override
    public boolean isFollowRelationExists(Long followeeId, Long followerId) {
        return followRelationRepository.existsByFollowee_idAndFollower_id(followeeId, followerId);
    }

    @Override
    public void createFollowRelation(User followee, User follower) {
        validateFollowAction(followee, follower);
        FollowRelation followRelation = FollowRelationConverter.toFollowRelation(followee, follower);
        followRelationRepository.save(followRelation);
    }

    @Override
    public void deleteFollowRelation(Long followeeId, Long followerId) {
        if (!followRelationRepository.existsByFollowee_idAndFollower_id(followeeId, followerId)) {
            throw new FollowRelationNotFoundException();
        }
        followRelationRepository.deleteByFolloweeIdAndFollowerId(followeeId, followerId);
    }

    @Override
    public List<User> getFollowersOfUser(User user) {
        return followRelationRepository.findFollowersOfUser(user);
    }

    @Override
    public Set<Long> getUserFollowingIds(User user) {
         return Set.copyOf(followRelationRepository.getUserFollowingIds(user.getId()));
    }

    @Override
    public List<User> getUserFollowings(User user) {
        return followRelationRepository.getUserFollowings(user);
    }

    private void validateFollowAction(User followee, User follower) {
        if (followee.getId().equals(follower.getId())) {
            throw new AttemptedSelfFollowException();
        }
        if (isFollowRelationExists(followee.getId(), follower.getId())) {
            throw new FollowRelationDuplicatedException();
        }
    }
}
