package protonaut.validation;

import io.micronaut.context.annotation.Factory;
import io.micronaut.validation.validator.constraints.ConstraintValidator;

import javax.inject.Singleton;

@Factory
public class ValidatorFactory {

    @Singleton
    ConstraintValidator<RealUserName, String> realUserNameValidator() {
        return (value, annotationMetadata, context) ->
                !"test".equals(value.toLowerCase());
    }
}
