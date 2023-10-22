package com.company.mariosy.service;

import com.company.mariosy.DTO.UserDTO;
import com.company.mariosy.entity.MariosEntity;
import com.company.mariosy.entity.UserEntity;
import com.company.mariosy.mappers.UserMapper;
import com.company.mariosy.repository.UserRepository;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDTO> getAllUsersDTOs() {
        return ImmutableList.copyOf(userRepository.findAll()).stream().map(u -> userMapper.userEntityToUserDTO(u)).collect(Collectors.toList());
    }

    public UserDTO createUser(UserDTO userDTO) throws IllegalUserFieldValueException {

        String firstName = userDTO.getFirstName();
        String lastName = userDTO.getLastName();
        String email = userDTO.getEmail();

        if (StringUtils.isBlank(email)) {
            throw new IllegalUserFieldValueException("email is blank");
        } else if (!userMapper.isEmailValid(email)) {
            throw new IllegalUserFieldValueException("email is not valid");
        } else if (StringUtils.isBlank(firstName)) {
            throw new IllegalUserFieldValueException("firstName is blank");
        } else if (!StringUtils.isAlpha(firstName)) {
            throw new IllegalUserFieldValueException("firstName contains non alpha characters");
        } else if (StringUtils.isBlank(lastName)) {
            throw new IllegalUserFieldValueException("lastName is blank");
        } else if (!StringUtils.isAlpha(lastName)) {
            throw new IllegalUserFieldValueException("lastName contains non alpha characters");
        }

        Optional<UserEntity> userWithThisEmail;
        userWithThisEmail = userRepository.findUserByEmail(email);

        if (userWithThisEmail.isPresent()) {
            throw new IllegalUserFieldValueException("User with this email already exists");
        }
        UserEntity userEntity = new UserEntity(firstName, lastName, email);
        userRepository.save(userEntity);
        return userMapper.userEntityToUserDTO(userEntity);
    }

    public Optional<UserDTO> getUserDTOByExternalId(UUID externalId) {
        return userMapper.optionalUserEntityToOptionalUserDTO(userRepository.findUserByExternalId(externalId));
    }

    public Optional<UserDTO> getUserDTOByEmail(String email) {
        return userMapper.optionalUserEntityToOptionalUserDTO(userRepository.findUserByEmail(email));
    }

    public List<UserDTO> searchUsersDTOs(String searchKeyword) {
        return userRepository.searchUserEntities(searchKeyword).stream().map(u -> userMapper.userEntityToUserDTO(u)).collect(Collectors.toList());
    }

    public void deleteUser(UUID userExternalId) {
        Optional<UserEntity> userEntityOptional = userRepository.findUserByExternalId(userExternalId);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            Set<MariosEntity> receivedMarioses = userEntity.getReceived_marioses();
            for (MariosEntity mariosEntity : receivedMarioses) {
                mariosEntity.removeReceiver(userEntity);
            }
            userRepository.delete(userEntity);
        }
    }

}
