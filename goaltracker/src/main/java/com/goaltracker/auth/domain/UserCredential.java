package com.goaltracker.auth.domain;

import com.goaltracker.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "user_credentials")
@Getter
@Setter
public class UserCredential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hashedPassword;

    @ManyToMany
    @JoinTable(
            name = "user_credential_authorities",
            joinColumns = {@JoinColumn(name = "user_credential_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

    @OneToOne(mappedBy = "userCredential")
    private User user;
}