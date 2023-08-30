package com.goaltracker.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "interests")
@Getter
@Setter
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tagName;

    @ManyToMany(mappedBy = "interests")
    private Set<UserProfile> userProfiles = new HashSet<>();
}