package protonaut.configuration;

import io.micronaut.context.annotation.Factory;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import protonaut.converter.PersonCustomMapper;
import protonaut.entity.OrderEntity;
import protonaut.entity.PersonEntity;
import protonaut.entity.UserEntity;
import protonaut.model.Order;
import protonaut.model.Person;
import protonaut.model.User;

import javax.inject.Singleton;

@Factory
public class OrikaConverterConfiguration {

    @Singleton
    public MapperFactory mapperFactory() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory
                .classMap(UserEntity.class, User.class)
                .field("id", "userId")
                .byDefault()
                .register();

        mapperFactory.classMap(PersonEntity.class, Person.class)
                .customize(new PersonCustomMapper())
                .byDefault()
                .register();
        mapperFactory.classMap(OrderEntity.class, Order.class)
                .byDefault()
                .register();

        return mapperFactory;
    }
}
