package com.company.mariosy.repository;

import com.company.mariosy.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findUserByEmail(String str);

    Optional<UserEntity> findUserById(Long id);

    Optional<UserEntity> findUserByExternalId(UUID externalId);

    List<UserEntity> findUsersByExternalIdIn(Set<UUID> externalIds);

    @Query("SELECT u FROM user_account u WHERE "
            + " lower(u.firstName) LIKE lower(concat('%', ?1,'%'))"
            + " OR lower(u.lastName) LIKE lower(concat('%', ?1,'%'))"
            + " OR lower(u.email) LIKE lower(concat('%', ?1,'%'))"
    )
    List<UserEntity> searchUserEntities(String searchKeyword);
}
