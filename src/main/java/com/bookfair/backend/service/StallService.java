
package com.bookfair.backend.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookfair.backend.dto.stall.mapper.StallMapper;
import com.bookfair.backend.dto.stall.request.CreateStallRequest;
import com.bookfair.backend.dto.stall.request.UpdateStallRequest;
import com.bookfair.backend.dto.stall.response.StallResponse;
import com.bookfair.backend.exception.ErrorCode;
import com.bookfair.backend.exception.ResourceNotFoundException;
import com.bookfair.backend.model.Stall;
import com.bookfair.backend.repository.StallRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StallService {

    private final StallRepository stallRepository;
    private final StallMapper stallMapper;

    @Transactional
    public List<StallResponse> createStall(List<CreateStallRequest> stallRequests) {
        List<Stall> stalls = stallRequests.stream().map(stallRequest -> {
                return stallMapper.toStallFromCreateStallRequest(stallRequest);
            }
        ).toList();

        List<Stall> savedStalls = stallRepository.saveAll(stalls);

        return savedStalls.stream().map( savedStall -> {
                return stallMapper.toStallResponse(savedStall);
            }
        ).toList();
    }

    public StallResponse getStallById(UUID id) {
        Stall stall = stallRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Physical Stall not found", ErrorCode.STALL_NOT_FOUND));
        
        return stallMapper.toStallResponse(stall);
    }

    @Transactional
    public StallResponse updateStall(UUID id, UpdateStallRequest stallRequest) {
        Stall stall = stallRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Physical Stall not found", ErrorCode.STALL_NOT_FOUND));

        stallMapper.updateStallFromStallRequest(stallRequest, stall);

        Stall updatedStall = stallRepository.save(stall);

        return stallMapper.toStallResponse(updatedStall);
    }

    public List<StallResponse> getAvailableStalls() {
        return stallRepository.findAllByActiveTrue().stream()
                .map(stall -> {
                    return stallMapper.toStallResponse(stall);
                }).toList();
    }

    @Transactional
    public void deactivateStall(List<UUID> id) {

        List<Stall> stalls = stallRepository.findAllByIdInAndActiveTrue(id);

        for(Stall stall : stalls) {
            stall.setActive(false);
        }

        stallRepository.saveAll(stalls);
    }
    
}
