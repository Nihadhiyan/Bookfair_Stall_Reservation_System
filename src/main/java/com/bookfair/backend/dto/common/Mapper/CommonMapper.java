package com.bookfair.backend.dto.common.Mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.bookfair.backend.dto.common.LayoutMarkerDto;
import com.bookfair.backend.dto.common.LayoutPositionDto;
import com.bookfair.backend.dto.config.GlobalMapperConfig;
import com.bookfair.backend.model.LayoutMarker;
import com.bookfair.backend.model.LayoutPosition;

@Mapper(config = GlobalMapperConfig.class)
public interface CommonMapper {
    LayoutPositionDto toLayoutPositionDto(LayoutPosition layoutPosition);

    LayoutMarkerDto toLayoutMarkerDto(LayoutMarker marker);

    List<LayoutMarkerDto> toLayoutMarkerDtos(List<LayoutMarker> markers);
}
