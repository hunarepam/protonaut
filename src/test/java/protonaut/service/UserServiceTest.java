package protonaut.service;

import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import protonaut.entity.UserEntity;
import protonaut.model.User;
import protonaut.repository.UserRepository;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
class UserServiceTest {

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Test
    void createInvalid() {
        User invalidUser = new User();
        invalidUser.setUsername("1234467");

        final ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () ->
                        userService.create(invalidUser)
                );

        assertEquals(1, exception.getConstraintViolations().size());
        assertTrue(exception.getMessage().contains("create.user.password: must not be blank"));
    }

    @Test
    void createTestNameUser() {
        User notUniqueUser = new User();
        notUniqueUser.setPassword("1514");
        notUniqueUser.setUsername("Test");

        final ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () ->
                        userService.create(notUniqueUser)
                );

        assertEquals(1, exception.getConstraintViolations().size());
        assertTrue(exception.getMessage().contains("not real userName (Test)"));
    }

    @Test
    void createNotUniqueUser() {
        String username = "Raman_Bandarenka";
        User notUniqueUser = new User();
        notUniqueUser.setPassword("1514");
        notUniqueUser.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new UserEntity()));

        final ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () ->
                        userService.create(notUniqueUser)
                );

        assertEquals(1, exception.getConstraintViolations().size());
        assertTrue(exception.getMessage().contains(String.format("not unique userName (%s)", username)));
    }

    @MockBean(value = UserRepository.class)
    UserRepository userRepository() {
        return mock(UserRepository.class);
    }
}