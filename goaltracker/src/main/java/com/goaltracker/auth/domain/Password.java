package com.goaltracker.auth.domain;

import com.goaltracker.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "passwords")
@Getter
@Setter
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String salt;
    private String hashedPassword;

    @OneToOne(mappedBy = "password")
    private User user;
}