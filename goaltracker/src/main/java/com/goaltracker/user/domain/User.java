package com.goaltracker.user.domain;

import com.goaltracker.auth.domain.UserCredential;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.HashSet;

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

    @OneToOne
    @JoinColumn(name = "user_credential_id")
    private UserCredential userCredential;

    private String username;
    private String email;

    @OneToMany(mappedBy = "follower")
    private Set<FollowRelation> following = new HashSet<>();

    @OneToMany(mappedBy = "followee")
    private Set<FollowRelation> followers = new HashSet<>();
}