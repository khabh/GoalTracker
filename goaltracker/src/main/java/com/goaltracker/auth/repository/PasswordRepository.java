package com.goaltracker.auth.repository;

import com.goaltracker.auth.domain.Password;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<Password, Long> {
}
