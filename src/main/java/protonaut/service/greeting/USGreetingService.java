package protonaut.service.greeting;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;

import javax.inject.Singleton;

@Singleton
@Requires(property = "app.country", value = "US")
@Replaces(value = GreetingService.class)
public class USGreetingService implements GreetingService {
    @Override
    public String greet() {
        return "Hi folks!";
    }
}
