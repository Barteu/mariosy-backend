package com.company.mariosy.service;

import com.company.mariosy.repository.UserRepository;
import com.company.mariosy.DTO.UserDTO;
import com.company.mariosy.entity.MariosEntity;
import com.company.mariosy.entity.UserEntity;
import com.company.mariosy.mappers.UserMapper;
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

    public UserDTO createUser(UUID externalId, String username) throws IllegalUserFieldValueException {

        if (StringUtils.isBlank(username)) {
            throw new IllegalUserFieldValueException("username is blank");
        }else if(externalId == null){
            throw new IllegalUserFieldValueException("externalId is null");
        }

        Optional<UserEntity> userWithThisUsername;
        userWithThisUsername = userRepository.findUserByUsername(username);

        if (userWithThisUsername.isPresent()) {
            throw new IllegalUserFieldValueException("User with this username already exists");
        }
        UserEntity userEntity = new UserEntity(externalId, username);
        userRepository.save(userEntity);
        return userMapper.userEntityToUserDTO(userEntity);
    }

    public Optional<UserDTO> getUserDTOByExternalId(UUID externalId) {
        return userMapper.optionalUserEntityToOptionalUserDTO(userRepository.findUserByExternalId(externalId));
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
