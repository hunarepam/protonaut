package protonaut.validation;

import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.validation.validator.constraints.ConstraintValidator;
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext;
import org.jetbrains.annotations.NotNull;
import protonaut.repository.UserRepository;

import javax.inject.Singleton;

@Singleton
public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserName, String> {

    private final UserRepository userRepository;

    public UniqueUserNameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(@NotNull String username,
                           @NotNull AnnotationValue<UniqueUserName> annotationMetadata,
                           @NotNull ConstraintValidatorContext context) {
        return !userRepository.findByUsername(username).isPresent();
    }

}
