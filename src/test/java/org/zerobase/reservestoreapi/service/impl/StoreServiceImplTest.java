package org.zerobase.reservestoreapi.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.zerobase.reservestoreapi.repository.StoreRepository;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class StoreServiceImplTest {
    @InjectMocks private StoreServiceImpl storeService;
    @Mock private StoreRepository storeRepository;

    @DisplayName("Get all stores")
    @Test
    void searchStores() {
        //given
        //when
        storeService.searchStores();
        //then
    }

    @DisplayName("Get store detail info by storeId")
    @Test
    void searchStore() {
        //given
        Long storeId = 1L;
        //when
        storeService.searchStore(storeId);
        //then
    }
}