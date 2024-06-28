package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yandex.practicum.filmorate.validator.RealiseDateContraint;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class Film {
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @Size (max = 200)
    private String description;
    @NotNull
    @RealiseDateContraint
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    @Positive
    @NotNull
    private int duration;
}
