package com.bookfair.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.bookfair.backend.dto.request.GenreRequest;
import com.bookfair.backend.dto.response.GenreResponse;
import com.bookfair.backend.service.GenreService;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;
    
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<GenreResponse>> getAllGenres() {
        return ResponseEntity.ok(genreService.getAllGenres());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<GenreResponse> getGenreById(Long id) {
        return ResponseEntity.ok(genreService.getGenreById(id));
    }

    @PostMapping
    public ResponseEntity<GenreResponse> createGenre(GenreRequest genreRequest) {
        return ResponseEntity.ok(genreService.createGenre(genreRequest));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GenreResponse> updateGenre(Long id, GenreRequest genreRequest) {
        return ResponseEntity.ok(genreService.updateGenre(id, genreRequest));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }


}
