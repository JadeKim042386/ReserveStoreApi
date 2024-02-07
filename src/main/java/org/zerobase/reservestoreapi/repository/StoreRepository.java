package org.zerobase.reservestoreapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNullApi;
import org.zerobase.reservestoreapi.domain.Store;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @EntityGraph(attributePaths = {"member"})
    Page<Store> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"member"})
    Optional<Store> findById(Long storeId);

    boolean existsByName(String storeName);
}
