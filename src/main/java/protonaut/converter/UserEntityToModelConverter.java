package protonaut.converter;

import io.micronaut.core.convert.ConversionContext;
import io.micronaut.core.convert.TypeConverter;
import protonaut.entity.UserEntity;
import protonaut.model.User;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class UserEntityToModelConverter implements TypeConverter<UserEntity, User> {
    @Override
    public Optional<User> convert(UserEntity source, Class<User> targetType, ConversionContext context) {
        if (source == null) {
            return Optional.empty();
        }
        User user = new User();
        user.setUsername(source.getUsername());
        user.setPassword(source.getPassword());
        user.setUserId(source.getId());
        return Optional.of(user);
    }
}
