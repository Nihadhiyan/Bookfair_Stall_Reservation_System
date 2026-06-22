package com.bookfair.backend.dto.building.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.bookfair.backend.dto.building.request.CreateBuildingRequest;
import com.bookfair.backend.dto.building.request.UpdateBuildingRequest;
import com.bookfair.backend.dto.building.response.BuildingResponse;
import com.bookfair.backend.dto.common.Mapper.CommonMapper;
import com.bookfair.backend.dto.config.GlobalMapperConfig;
import com.bookfair.backend.model.Building;

@Mapper(
    config = GlobalMapperConfig.class,
    uses = {CommonMapper.class}
)
public interface BuildingMapper {
    BuildingResponse toBuildingResponse(Building building);

    Building toBuildingFromCreateBuildingRequest(CreateBuildingRequest request);

    Building updateBuildingFromBuildingRequest(UpdateBuildingRequest request, @MappingTarget Building building);
}
