package protonaut.service.greeting;

import io.micronaut.context.annotation.DefaultImplementation;

@DefaultImplementation(DefaultGreetingService.class)
public interface GreetingService {
    String greet();
}
