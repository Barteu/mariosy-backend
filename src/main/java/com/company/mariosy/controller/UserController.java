package com.company.mariosy.controller;

import com.company.mariosy.DTO.UserDTO;
import com.company.mariosy.service.IllegalUserFieldValueException;
import com.company.mariosy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsersDTOs();
    }

    @GetMapping(value = "/users", params = "searchKeyword")
    public List<UserDTO> searchUsers(@RequestParam("searchKeyword") String searchKeyword) {
        return userService.searchUsersDTOs(searchKeyword);
    }

    @GetMapping(value = "/users", params = "email")
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam String email) {
        Optional<UserDTO> userDTOOptional = userService.getUserDTOByEmail(email);
        if (userDTOOptional.isPresent()) {
            return new ResponseEntity<>(userDTOOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users/{userExternalId}")
    public ResponseEntity<UserDTO> getUserByExternalId(@PathVariable UUID userExternalId) {
        Optional<UserDTO> userDTOOptional = userService.getUserDTOByExternalId(userExternalId);
        if (userDTOOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(userDTOOptional.get(), HttpStatus.OK);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        try {
            UserDTO responseUserDTO = userService.createUser(userDTO);
            return new ResponseEntity<>(responseUserDTO, HttpStatus.CREATED);
        } catch (IllegalUserFieldValueException e) {
            UserDTO emptyUserDTO = new UserDTO();
            emptyUserDTO.setAdditionalMessage(e.getMessage());
            return new ResponseEntity<>(emptyUserDTO, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users/{userExternalId}")
    public ResponseEntity deleteUser(@PathVariable UUID userExternalId) {
        userService.deleteUser(userExternalId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
