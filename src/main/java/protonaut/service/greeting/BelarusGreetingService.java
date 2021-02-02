package protonaut.service.greeting;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;

import javax.inject.Singleton;

@Singleton
@Requires(property = "app.country", value = "BY")
@Replaces(value = GreetingService.class)
public class BelarusGreetingService implements GreetingService {
    @Override
    public String greet() {
        return "Vitaju siabry";
    }
}
