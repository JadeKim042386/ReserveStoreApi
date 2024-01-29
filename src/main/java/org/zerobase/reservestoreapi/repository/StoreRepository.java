package org.zerobase.reservestoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerobase.reservestoreapi.domain.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
    boolean existsByName(String storeName);
}
