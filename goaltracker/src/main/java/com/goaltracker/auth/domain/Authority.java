package com.goaltracker.auth.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "authorities")
@Getter
@NoArgsConstructor
public class Authority {

    @Id
    @Column(name = "authority_name", length = 50, unique = true)
    private String authorityName;

    @ManyToMany(mappedBy = "authorities")
    Set<UserCredential> userCredentials;

    @Builder
    public Authority(String authorityName) {
        this.authorityName = authorityName;
    }
}