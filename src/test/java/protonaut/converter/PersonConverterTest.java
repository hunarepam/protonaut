package protonaut.converter;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import protonaut.entity.PersonEntity;
import protonaut.model.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("unit")
@ExtendWith({MockitoExtension.class})
public class PersonConverterTest {
    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private MapperFacade mapper;

    @BeforeEach
    void setup() {
        mapperFactory.classMap(PersonEntity.class, Person.class)
                .customize(new PersonCustomMapper())
                .byDefault()
                .register();
        mapper = mapperFactory.getMapperFacade();
    }

    @Test
    void convertFromEntity() {

        PersonEntity entity = new PersonEntity();
        entity.setLastname("Kalinouski");
        entity.setFirstname("Kastus`");
        entity.setAge(25);
        entity.setSex("Male");

        Person person = mapper.map(entity, Person.class);

        assertEquals(entity.getAge(), person.getAge());
        assertEquals(entity.getSex(), person.getSex());
        assertEquals("Kastus` Kalinouski", person.getFullName());
    }

    @Test
    void convertToEntity() {
        Person model = new Person();
        model.setFullName("Kastus` Kalinouski");
        model.setAge(25);
        model.setSex("Male");

        PersonEntity entity = mapper.map(model, PersonEntity.class);

        assertEquals(model.getAge(), entity.getAge());
        assertEquals(model.getSex(), entity.getSex());
        assertEquals("Kastus`", entity.getFirstname());
        assertEquals("Kalinouski", entity.getLastname());

    }
}
