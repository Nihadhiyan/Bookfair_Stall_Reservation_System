package com.bookfair.backend.dto.hall.response;

import java.util.List;
import java.util.UUID;

import com.bookfair.backend.dto.common.LayoutMarkerDto;
import com.bookfair.backend.dto.stall.response.StallResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HallLayoutResponse {
    private UUID id;
    private String name;
    private String spaceCategory;
    private String hallType;
    private List<LayoutMarkerDto> markers;
    private List<StallResponse> stalls;
}
