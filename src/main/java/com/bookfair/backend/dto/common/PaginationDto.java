package com.bookfair.backend.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDto {
    private Integer page;

    private Integer size;

    private Long totalElements;
    
    private Integer totalPages;
}
