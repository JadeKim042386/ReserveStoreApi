package org.zerobase.reservestoreapi.service.impl;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.zerobase.reservestoreapi.domain.Member;
import org.zerobase.reservestoreapi.domain.Review;
import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.domain.constants.Address;
import org.zerobase.reservestoreapi.domain.constants.MemberRole;
import org.zerobase.reservestoreapi.domain.constants.StoreType;
import org.zerobase.reservestoreapi.dto.StoreDto;
import org.zerobase.reservestoreapi.dto.StoreWithReviewDto;
import org.zerobase.reservestoreapi.repository.StoreRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class StoreServiceImplTest {
    @InjectMocks private StoreServiceImpl storeService;
    @Mock private StoreRepository storeRepository;

    @DisplayName("Get all stores")
    @Test
    void searchStores() throws IllegalAccessException {
        //given
        Pageable pageable = PageRequest.of(0, 10);
        given(storeRepository.findAll(any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(createStore()), pageable, 1));
        //when
        Page<StoreDto> storeDtos = storeService.searchStores(pageable);
        //then
        assertThat(storeDtos.getTotalElements()).isEqualTo(1);
        assertThat(storeDtos.getContent().get(0).name()).isEqualTo("name");
    }

    @DisplayName("Get store detail info by storeId")
    @Test
    void searchStore() throws IllegalAccessException {
        //given
        Long storeId = 1L;
        given(storeRepository.findById(anyLong()))
                .willReturn(Optional.of(createStore()));
        //when
        StoreWithReviewDto storeWithReviewDto = storeService.searchStore(storeId);
        //then
        assertThat(storeWithReviewDto.storeDto().name()).isEqualTo("name");
        assertThat(storeWithReviewDto.reviews().size()).isEqualTo(1);
    }

    private static Store createStore() throws IllegalAccessException {
        Store store = Store.of(
                "name",
                LocalTime.of(9, 0),
                LocalTime.of(18, 0),
                30,
                StoreType.CAFFE
        );
        Member member = Member.ofMember(
                "username",
                "pw",
                "nickname",
                MemberRole.MEMBER,
                Address.of("12345", "street", "detail"),
                "01012341234"
        );
        Review review = Review.of("content", 5);
        FieldUtils.writeField(store, "member", member, true);
        LocalDateTime now = LocalDateTime.now();
        FieldUtils.writeField(review, "createdAt", now, true);
        FieldUtils.writeField(review, "modifiedAt", now, true);
        FieldUtils.writeField(review, "createdBy", "admin", true);
        FieldUtils.writeField(review, "modifiedBy", "admin", true);
        store.getReviews().add(review);
        return store;
    }

    @DisplayName("check already exists store name")
    @Test
    void isExistsStoreName() {
        //given
        String storeName = "store";
        given(storeRepository.existsByName(anyString()))
                .willReturn(false);
        //when
        assertThatNoException()
                .isThrownBy(() -> storeService.isExistsStoreName(storeName));
        //then
    }
}
