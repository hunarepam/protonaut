package protonaut.converter;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import protonaut.entity.PersonEntity;
import protonaut.model.Person;

public class PersonCustomMapper extends CustomMapper<PersonEntity, Person> {

    @Override
    public void mapAtoB(PersonEntity entity, Person model, MappingContext context) {
        model.setFullName(String.format("%s %s", entity.getFirstname(), entity.getLastname()));
    }

    public void mapBtoA(Person model, PersonEntity entity, MappingContext context) {
        String fullName = model.getFullName();
        int separationIndex = fullName.indexOf(" ");
        entity.setFirstname(fullName.substring(0, separationIndex));
        entity.setLastname(fullName.substring(separationIndex + 1));
    }
}
