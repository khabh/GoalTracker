package com.goaltracker.auth.service;

import com.goaltracker.auth.domain.UserCredential;
import com.goaltracker.auth.dto.InitialPasswordDTO;
import com.goaltracker.user.domain.User;

public interface UserCredentialService {
    UserCredential generateSaltedPassword(InitialPasswordDTO passwordDTO);
    String generateToken(User user);
}
