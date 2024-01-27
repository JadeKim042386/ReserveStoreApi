package org.zerobase.reservestoreapi.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.zerobase.reservestoreapi.repository.ReviewRepository;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {
    @InjectMocks private ReviewServiceImpl reviewService;
    @Mock private ReviewRepository reviewRepository;

    @DisplayName("get average rating")
    @Test
    void getAverageRatingByStoreId() {
        //given
        Long storeId = 1L;
        //when
        Float rating = reviewService.getAverageRatingByStoreId(storeId);
        //then
    }
}