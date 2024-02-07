package org.zerobase.reservestoreapi.dto.response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;

public record PagedResponse<T>(
        List<T> content,
        int number,
        int size,
        JsonNode sort,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last) {
    public static <T> PagedResponse<T> of(Page<T> page) {
        return new PagedResponse<>(
                Collections.unmodifiableList(page.getContent()),
                page.getNumber(),
                page.getSize(),
                sortToJsonNode(page.getSort()),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast());
    }

    /**
     *
     *
     * <pre>
     * Example:
     * <code>JsonNode jsonNode = sortToJsonNode(sort);</code>
     * <code>String property = jsonNode.get(0).get("property");</code>
     * </pre>
     *
     * @return [ { "direction":"DESC", "property":"name", "ignoreCase":false,
     *     "nullHandling":"NATIVE", "descending":true, "ascending":false }, ... ]
     */
    private static JsonNode sortToJsonNode(Sort sort) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.valueToTree(sort.iterator());
    }
}
