package com.bookfair.backend.dto.hall.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.bookfair.backend.dto.common.Mapper.CommonMapper;
import com.bookfair.backend.dto.config.GlobalMapperConfig;
import com.bookfair.backend.dto.hall.request.CreateHallRequest;
import com.bookfair.backend.dto.hall.request.UpdateHallRequest;
import com.bookfair.backend.dto.hall.response.HallLayoutResponse;
import com.bookfair.backend.dto.hall.response.HallResponse;
import com.bookfair.backend.model.Hall;

@Mapper(
    config = GlobalMapperConfig.class,
    uses = {CommonMapper.class}
)
public interface HallMapper {
    HallResponse toHallResponse(Hall hall);

    HallLayoutResponse toHallLayoutResponse(Hall hall);

    Hall toHallFromCreateHallRequest(CreateHallRequest request);

    Hall UpdateHallFromHallRequest(UpdateHallRequest request, @MappingTarget Hall hall);
}
