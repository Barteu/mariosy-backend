package com.company.mariosy.mappers;

import com.company.mariosy.DTO.UserDTO;
import com.company.mariosy.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserMapper {

    public UserMapper() {
    }

    public UserDTO userEntityToUserDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setExternalId(userEntity.getExternalId());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setFirstName(userEntity.getFirstName());
        userDTO.setLastName(userEntity.getLastName());
        return userDTO;
    }

    public Optional<UserDTO> optionalUserEntityToOptionalUserDTO(Optional<UserEntity> userEntityOptional) {
        if (userEntityOptional.isPresent()) {
            return Optional.ofNullable(userEntityToUserDTO(userEntityOptional.get()));
        }
        return Optional.ofNullable(null);
    }

    public boolean isEmailValid(String email) {

        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern pattern = java.util.regex.Pattern.compile(ePattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

}