package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint (validatedBy = RealiseDateValidator.class)
@Target ({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RealiseDateContraint {
    String message() default "Film realise must be after 1985";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
