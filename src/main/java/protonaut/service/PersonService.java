package protonaut.service;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import protonaut.entity.PersonEntity;
import protonaut.model.Person;
import protonaut.repository.PersonRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PersonService {

    private final PersonRepository personRepository;
    private final MapperFacade mapper;

    @Inject
    public PersonService(PersonRepository personRepository, MapperFactory mapperFactory) {
        this.personRepository = personRepository;
        this.mapper = mapperFactory.getMapperFacade();
    }

    public Person save(Person person) {
        PersonEntity personEntity = mapper.map(person, PersonEntity.class);
        personEntity = personRepository.save(personEntity);
        return mapper.map(personEntity, Person.class);
    }

    public void createDefaultPersonByUsername(String userName) {
        Person person = new Person();
        person.setFullName(userName.replace("_", " "));
        person.setSex("Male");
        save(person);
    }
}
