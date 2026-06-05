package com.bookfair.backend.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookfair.backend.dto.bookfair.response.BookFairResponse;
import com.bookfair.backend.dto.bookfair.response.BookFairStallResponse;
import com.bookfair.backend.service.BookFairService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookfairs")
public class BookFairController {
    private final BookFairService bookFairService;

    @GetMapping
    public ResponseEntity<List<BookFairResponse>> getUpcomingBookFairs() {
        return ResponseEntity.ok(bookFairService.getUpcomingBookFairs());
    }

    @GetMapping("/{bookFairId}/stalls")
    public ResponseEntity<List<BookFairStallResponse>> getStallsForEvent(@PathVariable UUID bookFairId) {
        return ResponseEntity.ok(bookFairService.getStallsForEvent(bookFairId));
    }
}
