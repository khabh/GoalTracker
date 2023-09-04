package com.goaltracker.user.service;

import com.goaltracker.auth.dto.InitialPasswordDTO;
import com.goaltracker.user.dto.CreateUserDTO;

public interface UserService {
    String signUpNewUser(CreateUserDTO createUserDTO, InitialPasswordDTO initialPasswordDTO);
}
