package org.zerobase.reservestoreapi.dto;

public record ReviewRequest(
        String content,
        Integer rating
) {
}
