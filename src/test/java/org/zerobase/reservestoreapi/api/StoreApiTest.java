package org.zerobase.reservestoreapi.api;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.zerobase.reservestoreapi.config.TestSecurityConfig;
import org.zerobase.reservestoreapi.domain.Member;
import org.zerobase.reservestoreapi.domain.Review;
import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.domain.constants.Address;
import org.zerobase.reservestoreapi.domain.constants.MemberRole;
import org.zerobase.reservestoreapi.domain.constants.StoreType;
import org.zerobase.reservestoreapi.dto.StoreDto;
import org.zerobase.reservestoreapi.dto.StoreWithReviewDto;
import org.zerobase.reservestoreapi.dto.response.PagedResponse;
import org.zerobase.reservestoreapi.service.StoreService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
@WebMvcTest(StoreApi.class)
class StoreApiTest {
    @Autowired private MockMvc mvc;
    @MockBean private StoreService storeService;

    @DisplayName("search all stores")
    @Test
    void searchAllStores() throws Exception {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        given(storeService.searchStores(any()))
                .willReturn(PagedResponse.of(new PageImpl<>(List.of(createStoreDto()), pageable, 1)));
        // when
        mvc.perform(get("/api/v1/stores"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(1));
        // then
    }

    @DisplayName("search specific store")
    @Test
    void searchStore() throws Exception {
        // given
        Long storeId = 1L;
        given(storeService.searchStoreDto(anyLong()))
                .willReturn(createStoreDto());
        // when
        mvc.perform(get("/api/v1/stores/" + storeId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("store"));
        // then
    }

    private StoreDto createStoreDto() throws IllegalAccessException {
        Store store = createStore();
        return StoreDto.fromEntity(store);
    }

    private Store createStore() throws IllegalAccessException {
        Store store = Store.of("store", LocalTime.of(9, 0), LocalTime.of(18, 0), 30, 11.1f, StoreType.BAR);
        FieldUtils.writeField(store, "member", createMember(), true);
        store.getReviews().add(createReview(store));
        return store;
    }

    private Review createReview(Store store) throws IllegalAccessException {
        Review review = Review.of("content", 5, store);
        FieldUtils.writeField(review, "createdAt", LocalDateTime.now(), true);
        FieldUtils.writeField(review, "modifiedAt", LocalDateTime.now(), true);
        FieldUtils.writeField(review, "createdBy", "admin", true);
        FieldUtils.writeField(review, "modifiedBy", "admin", true);
        return review;
    }

    private Member createMember() {
        return Member.ofMember(
                "testUser",
                "pw",
                "nickname",
                MemberRole.MEMBER,
                Address.of("zipcode", "street", "detail"),
                "01011111111");
    }
}
