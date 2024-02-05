package org.zerobase.reservestoreapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerobase.reservestoreapi.domain.Store;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @EntityGraph(attributePaths = {"member"})
    Page<Store> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"member", "reviews"})
    Optional<Store> findById(Long storeId);

    boolean existsByName(String storeName);

    boolean existsByIdAndMember_Username(Long storeId, String username);
}
