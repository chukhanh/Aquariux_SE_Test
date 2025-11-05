package org.aquariux.cryptotrade.repository;

import org.aquariux.cryptotrade.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity>, PagingAndSortingRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
