package com.goaltracker.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "interests")
@Getter
@Setter
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tagName;

    @OneToMany(mappedBy = "interest")
    private List<UserProfileInterest> userProfileInterests;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interest interest = (Interest) o;
        return Objects.equals(id, interest.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}