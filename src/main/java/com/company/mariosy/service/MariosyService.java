package com.company.mariosy.service;

import com.company.mariosy.DTO.MariosDTO;
import com.company.mariosy.entity.MariosEntity;
import com.company.mariosy.entity.MariosType;
import com.company.mariosy.entity.UserEntity;
import com.company.mariosy.mappers.MariosMapper;
import com.company.mariosy.repository.MariosRepository;
import com.company.mariosy.repository.UserRepository;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class MariosyService {
    private final UserRepository userRepository;

    private final MariosRepository mariosRepository;

    private final MariosMapper mariosMapper;

    @Autowired
    public MariosyService(UserRepository userRepository, MariosRepository mariosRepository, MariosMapper mariosMapper) {
        this.userRepository = userRepository;
        this.mariosRepository = mariosRepository;
        this.mariosMapper = mariosMapper;
    }

    public List<MariosDTO> getMariosesDTOs() {
        return ImmutableList.copyOf(mariosRepository.findAll()).stream().map(m -> mariosMapper.mariosEntityToMariosDTO(m)).collect(Collectors.toList());
    }

    public List<MariosDTO> getPaginatedMariosesDTOs(Integer page, Integer size) {
        Pageable pageableSortedByCreation = PageRequest.of(page, size, Sort.by("creationInstant").descending());
        return ImmutableList.copyOf(mariosRepository.findAllBy(pageableSortedByCreation)).stream().map(m -> mariosMapper.mariosEntityToMariosDTO(m)).collect(Collectors.toList());
    }

    public MariosDTO createMarios(MariosDTO mariosDTO) throws IllegalMariosFieldValueException {

        UUID creatorExternalId = mariosDTO.getCreatorExternalId();
        Set<UUID> receiversExternalIds = mariosDTO.getReceiversExternalIds();
        MariosType mariosType = mariosDTO.getType();
        String comment = mariosDTO.getComment();
        String title = mariosDTO.getTitle();

        if (Objects.isNull(creatorExternalId)) {
            throw new IllegalMariosFieldValueException("creatorId is null");
        } else if (receiversExternalIds.isEmpty()) {
            throw new IllegalMariosFieldValueException("receiversIds is empty");
        } else if (receiversExternalIds.contains(creatorExternalId)) {
            throw new IllegalMariosFieldValueException("receiversIds contains creatorId");
        } else if (!MariosType.checkIfTypeExists(mariosType)) {
            throw new IllegalMariosFieldValueException("mariosType does not exists");
        } else if (StringUtils.isBlank(title)) {
            throw new IllegalMariosFieldValueException("title is blank");
        } else if (StringUtils.isBlank(comment)) {
            throw new IllegalMariosFieldValueException("comment is blank");
        } else if (comment.length() > MariosEntity.MAX_COMMENT_LENGTH) {
            throw new IllegalMariosFieldValueException("comment is too long");
        } else if (title.length() > MariosEntity.MAX_TITLE_LENGTH) {
            throw new IllegalMariosFieldValueException("title is too long");
        } else if (!MariosType.checkIfTypeExists(mariosType)) {
            throw new IllegalMariosFieldValueException("marios type does not exists");
        } else {
            Optional<UserEntity> optionalCreator = userRepository.findUserByExternalId(creatorExternalId);
            List<UserEntity> receivers = userRepository.findUsersByExternalIdIn(receiversExternalIds);

            if (optionalCreator.isEmpty()) {
                throw new IllegalMariosFieldValueException("Creator does not exists");
            }
            if (receivers.isEmpty() || receivers.size() != receiversExternalIds.size()) {
                throw new IllegalMariosFieldValueException("Receivers are not correct");
            }
            MariosEntity mariosEntity = new MariosEntity(mariosType, comment, title);
            mariosEntity.setCreator(optionalCreator.get());
            mariosEntity.setReceivers(Sets.newHashSet(receivers));
            mariosRepository.save(mariosEntity);
            return mariosMapper.mariosEntityToMariosDTO(mariosEntity);
        }
    }

    public List<MariosDTO> getMariosesDTOsCreatedByUser(UUID creatorExternalId) {
        return ImmutableList.copyOf(mariosRepository.findMariosEntitiesByCreator_ExternalIdOrderByCreationInstantDesc(creatorExternalId)).stream().map(m -> mariosMapper.mariosEntityToMariosDTO(m)).collect(Collectors.toList());
    }

    public List<MariosDTO> getMariosesDTOsReceivedByUser(UUID receiverExternalId) {

        Optional<UserEntity> userEntity = userRepository.findUserByExternalId(receiverExternalId);
        if (userEntity.isEmpty()) {
            throw new IllegalArgumentException("User does not exists");
        }
        Set<MariosEntity> marioses = userEntity.get().getReceived_marioses();
        return marioses.stream().map(m -> mariosMapper.mariosEntityToMariosDTO(m)).collect(Collectors.toList());
    }

    public Optional<MariosEntity> getMariosByExternalId(UUID externalId) {
        return mariosRepository.findMariosEntityByExternalId(externalId);
    }

    public Optional<MariosDTO> getMariosDTOByExternalId(UUID externalId) {
        return mariosMapper.optionalMariosEntityToOptionalMariosDTO(mariosRepository.findMariosEntityByExternalId(externalId));
    }

    public void deleteMarios(UUID mariosExternalId) {
        Optional<MariosEntity> mariosEntityOptional = mariosRepository.findMariosEntityByExternalId(mariosExternalId);
        if (mariosEntityOptional.isPresent()) {
            MariosEntity mariosEntity = mariosEntityOptional.get();
            mariosRepository.delete(mariosEntity);
        }
    }

    public List<MariosType> getMariosTypes() {
        return List.of(MariosType.values());
    }

}






































































































































