package com.goaltracker.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@DynamicUpdate
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "userProfile")
    private List<UserProfileInterest> userProfileInterests;

    private String nickname;
    private String profileImagePath;
    private String introduction;

    @OneToOne(mappedBy = "userProfile")
    private User user;
}
