package com.bookfair.backend.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookfair.backend.dto.genre.request.CreateGenreRequest;
import com.bookfair.backend.dto.genre.response.GenreResponse;
import com.bookfair.backend.service.GenreService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ORG_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<GenreResponse>> getAllGenres() {
        return ResponseEntity.ok(genreService.getAllGenres());
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<GenreResponse> createGenre(@Valid @RequestBody CreateGenreRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(genreService.createGenre(request));
    }

    @DeleteMapping("/{genreId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteGenre(@PathVariable UUID genreId) {
        genreService.deleteGenre(genreId);
        return ResponseEntity.noContent().build();
    }
}
