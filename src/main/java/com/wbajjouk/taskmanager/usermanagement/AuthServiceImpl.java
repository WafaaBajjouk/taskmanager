package com.wbajjouk.taskmanager.usermanagement;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
//    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

}
