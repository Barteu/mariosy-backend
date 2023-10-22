package com.company.mariosy.controller;

import com.company.mariosy.mappers.MariosMapper;
import com.company.mariosy.repository.MariosRepository;
import com.company.mariosy.repository.UserRepository;
import com.company.mariosy.service.MariosyService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class MariosControllerTest {

    @Mock
    MariosRepository mariosRepository;

    @Mock
    UserRepository userRepository;

    @Spy
    MariosMapper mariosMapper;

    @Spy
    MariosyService mariosyService;

    @InjectMocks
    MariosController mariosController;
}



