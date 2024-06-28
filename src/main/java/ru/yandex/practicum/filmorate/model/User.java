package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yandex.practicum.filmorate.validator.Marker;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    @Null(groups = Marker.Create.class)
    @NotNull(groups = Marker.Update.class)
    private Long id;
    private String name;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^\\S+$")
    private String login;
    @Email
    @NotBlank
    private String email;
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
}
