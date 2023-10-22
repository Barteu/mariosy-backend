package com.company.mariosy.repository;

import com.company.mariosy.entity.MariosEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MariosRepository extends PagingAndSortingRepository<MariosEntity, Long> {

    List<MariosEntity> findMariosEntitiesByCreator_ExternalIdOrderByCreationInstantDesc(UUID externalId);

    Optional<MariosEntity> findMariosEntityByExternalId(UUID externalId);

    List<MariosEntity> findAllBy(Pageable pageable);
}


