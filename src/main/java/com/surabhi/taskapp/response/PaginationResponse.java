package com.surabhi.taskapp.response;

import lombok.Data;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@Data
public class PaginationResponse<T> {
    private Long totalElements;
    private int totalPages;
    private Sort sort;
    private T data;
    private int size;
    private int page;

    public Long getTotalElements() {
        return Optional.ofNullable(totalElements).orElse(0L);
    }

    public int getTotalPages() {
        return Optional.ofNullable(totalElements).map(totalElement -> (int) (totalElement / size)).orElse(0);
    }

    public PaginationResponse() {
    }

    public PaginationResponse(T data, Long totalElements, int size, int page) {
        this.data = data;
        this.totalElements = totalElements;
        this.size = size;
        this.page = page;
    }

}
