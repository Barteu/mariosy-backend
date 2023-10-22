package com.company.mariosy.controller;

import com.company.mariosy.DTO.MariosDTO;
import com.company.mariosy.entity.MariosType;
import com.company.mariosy.service.IllegalMariosFieldValueException;
import com.company.mariosy.service.MariosyService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class MariosController {
    private MariosyService mariosyService;

    @Autowired
    public MariosController(MariosyService mariosyService) {
        this.mariosyService = mariosyService;
    }

    @GetMapping("/marioses")
    @PreAuthorize("hasRole('client_admin')")
    public List<MariosDTO> getAllMarioses() {
        return mariosyService.getMariosesDTOs();
    }

    @GetMapping(value = "/marioses", params = {"page", "size"})
    @PreAuthorize("hasRole('client_admin')")
    public List<MariosDTO> getAllMariosesWithPaginationAndOrder(@RequestParam Integer page, @RequestParam Integer size) {
        return mariosyService.getPaginatedMariosesDTOs(page, size);
    }

    @GetMapping("/marioses/{mariosExternalId}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<MariosDTO> getMariosById(@PathVariable("mariosExternalId") UUID mariosExternalId) {
        Optional<MariosDTO> mariosDTOOptional = mariosyService.getMariosDTOByExternalId(mariosExternalId);
        if (mariosDTOOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(mariosDTOOptional.get(), HttpStatus.OK);
        }
    }

    @PostMapping("/marioses")
    @PreAuthorize("hasAnyRole('client_user','client_admin')")
    public ResponseEntity<MariosDTO> createMarios(@RequestBody @NotNull MariosDTO mariosDTO) {
        try {
            MariosDTO returnMariosDTO = mariosyService.createMarios(mariosDTO);
            return new ResponseEntity<>(returnMariosDTO, HttpStatus.CREATED);
        } catch (IllegalMariosFieldValueException e) {
            MariosDTO emptyMariosDTO = new MariosDTO();
            emptyMariosDTO.setAdditionalMessage(e.getMessage());
            return new ResponseEntity<>(emptyMariosDTO, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{userExternalId}/marioses/created")
    @PreAuthorize("hasAnyRole('client_user','client_admin')")
    public ResponseEntity<List<MariosDTO>> getMariosesCreatedByUser(@PathVariable("userExternalId") UUID userExternalId, JwtAuthenticationToken token) {
        if (token.getName().equals(userExternalId.toString())){
            return new ResponseEntity<>(mariosyService.getMariosesDTOsCreatedByUser(userExternalId), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/{userExternalId}/marioses/received")
    @PreAuthorize("hasAnyRole('client_user','client_admin')")
    public ResponseEntity<List<MariosDTO>> getSortedMariosesReceivedByUser(@PathVariable UUID userExternalId, JwtAuthenticationToken token) {
        if (token.getName().equals(userExternalId.toString())){
            return new ResponseEntity<>(mariosyService.getMariosesDTOsReceivedByUser(userExternalId), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/marioses/{mariosExternalId}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity deleteMarios(@PathVariable UUID mariosExternalId) {
        mariosyService.deleteMarios(mariosExternalId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/marioses/types")
    public List<MariosType> getAllMariosTypes() {
        return mariosyService.getMariosTypes();
    }

}
