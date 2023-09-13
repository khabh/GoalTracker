package com.goaltracker.user.domain;

import com.goaltracker.auth.domain.UserCredential;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_credential_id")
    private UserCredential userCredential;

    private String username;
    private String email;

    @OneToMany(mappedBy = "follower")
    private List<FollowRelation> following = new ArrayList<>();

    @OneToMany(mappedBy = "followee")
    private List<FollowRelation> followers = new ArrayList<>();

    public boolean isFollowing(User user) {
        Long userId = user.getId();
        return following.stream()
                .map(FollowRelation::getFollowee)
                .anyMatch(followee -> followee.getId().equals(userId));
    }
}