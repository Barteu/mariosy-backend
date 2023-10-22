package com.company.mariosy.controller;

import com.company.mariosy.mappers.UserMapper;
import com.company.mariosy.repository.MariosRepository;
import com.company.mariosy.repository.UserRepository;
import com.company.mariosy.service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @Mock
    MariosRepository mariosRepository;

    @Mock
    UserRepository userRepository;

    @Spy
    UserMapper userMapper;

    @Spy
    UserService userService;

    @InjectMocks
    UserController userController;

}
