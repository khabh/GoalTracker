package com.goaltracker.auth.service;

import com.goaltracker.auth.domain.Password;
import com.goaltracker.auth.dto.InitialPasswordDTO;

public interface AuthService {
    Password generateSaltedPassword(InitialPasswordDTO passwordDTO);
}
