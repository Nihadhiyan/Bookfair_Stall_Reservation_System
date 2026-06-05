package com.bookfair.backend.dto.bookfair.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookFairRequest {

    @NotNull(message = "Book fair name is required")
    private String name;

    @NotNull(message = "Book fair location is required")
    private String location;

    @NotNull(message = "Book fair start date is required")
    private LocalDateTime startDateTime;

    @NotNull(message = "Book fair end date is required")
    private LocalDateTime endDateTime;

    @NotNull(message = "Book fair status is required")
    private String status;

    @NotNull(message = "Book fair active status is required")
    private Boolean active;
}
