package com.goaltracker.auth.repository;

import com.goaltracker.auth.domain.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {
}
