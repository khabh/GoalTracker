package com.goaltracker.user.domain;

import com.goaltracker.auth.domain.UserCredential;
import com.goaltracker.goal.domain.Goal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @OneToMany(mappedBy = "user")
    private List<Goal> goals;

    @OneToMany(mappedBy = "follower")
    private List<FollowRelation> following = new ArrayList<>();

    @OneToMany(mappedBy = "followee")
    private List<FollowRelation> followers = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}