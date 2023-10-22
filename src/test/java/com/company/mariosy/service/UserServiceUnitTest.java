package com.company.mariosy.service;

import com.company.mariosy.entity.UserEntity;
import com.company.mariosy.mappers.UserMapper;
import com.company.mariosy.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {

    @Mock
    UserRepository userRepository;

    @Spy
    UserMapper userMapper;

    @InjectMocks
    UserService userService;

    public List<UserEntity> users;
//
//    @BeforeEach
//    public void initTestData(){
//        this.users = new ArrayList<UserEntity>();
//        this.users.add(new UserEntity("Marta","Martowska","marta.marto@ko.com"));
//        this.users.add(new UserEntity("Jan","Janowski","janek@email.com"));
//        this.users.add(new UserEntity("Mariusz","Mariuszewicz","mariuszek@email.pl"));
//    }
//
//    @Test
//    public void shouldFindUser(){
//        //given
//        UserEntity user = this.users.get(2);
//        String searchKeyword = "mar";
//        when(userRepository.searchUserEntities(anyString())).thenReturn(Arrays.asList(this.users.get(0),this.users.get(2)));
//        //when
//        List<UserDTO> foundUsers = userService.searchUsersDTOs(searchKeyword);
//        //then
//        assertThat(foundUsers).filteredOn(u -> u.getExternalId().equals(user.getExternalId())).hasSize(1);
//    }
//
//    @Test
//    public void shouldNotFindAnyUser(){
//        //given
//        String searchKeyword = "xx";
//        when(userRepository.searchUserEntities(anyString())).thenReturn(Arrays.asList());
//        //when
//        List<UserDTO> foundUsers = userService.searchUsersDTOs(searchKeyword);
//        //then
//        assertThat(foundUsers).hasSize(0);
//    }
//
//    @Test
//    public void shouldGetUserByExternalId(){
//        //given
//        UserEntity user = this.users.get(0);
//        UUID externalId = user.getExternalId();
//        when(userRepository.findUserByExternalId(any(UUID.class))).thenReturn(this.users.stream().filter(u -> u.getExternalId().equals(externalId)).findFirst());
//        //when
//        Optional<UserDTO> userDTOOptional = userService.getUserDTOByExternalId(externalId);
//        //then
//        assertEquals(userDTOOptional.get().getExternalId(), externalId);
//    }
//
//    @Test
//    public void shouldNotGetUserByExternalId(){
//        //given
//        UUID externalId = UUID.randomUUID();
//        when(userRepository.findUserByExternalId(any(UUID.class))).thenReturn(this.users.stream().filter(u -> u.getExternalId().equals(externalId)).findFirst());
//        //when
//        Optional<UserDTO> userDTOOptional = userService.getUserDTOByExternalId(externalId);
//        //then
//        assertFalse(userDTOOptional.isPresent());
//    }

}