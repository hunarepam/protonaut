package protonaut.service;

//import io.micronaut.core.convert.ConversionService;
import io.micronaut.data.model.Pageable;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import protonaut.entity.UserEntity;
import protonaut.exception.NotFoundException;
import protonaut.model.Page;
import protonaut.model.User;
import protonaut.repository.UserRepository;

import javax.inject.Singleton;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class UserService {

    private final UserRepository userRepository;
    private final MapperFacade mapper;
    //private final ConversionService conversionService;

    public UserService(UserRepository userRepository,
                       //ConversionService conversionService,
                       MapperFactory mapperFactory) {
        this.userRepository = userRepository;
        this.mapper = mapperFactory.getMapperFacade();
        //this.conversionService = conversionService;
    }

    public String greet(String userName) {
        return String.format("Hello %s", userName);
    }

    public User create(@Valid User user) {
        UserEntity entityToSave = mapper.map(user, UserEntity.class);
        UserEntity savedEntity = userRepository.save(entityToSave);
        return mapper.map(savedEntity, User.class);
    }

    public User findById(Long userId) {
        Optional<UserEntity> existedUser = userRepository.findById(userId);
        if (existedUser.isPresent()) {
            //return (User) conversionService.convert(existedUser.get(), User.class).orElse(null);
            return mapper.map(existedUser.get(), User.class);
        }
        throw new NotFoundException(String.format("User %s not found", userId));
    }

    public List<User> findAll(Page page) {
         return userRepository.findAll(Pageable.from(page.getPage(), page.getSize()))
                 .getContent()
                .stream()
                //.map(userEntity -> conversionService.convert(userEntity, User.class))
                //.map(userOptional -> (User) userOptional.orElse(null))
                .map(userEntity -> mapper.map(userEntity, User.class))
                .collect(Collectors.toList());
    }
}
