package com.goaltracker.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_profile_interests")
public class UserProfileInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    @ManyToOne
    @JoinColumn(name = "interest_id")
    private Interest interest;
}
