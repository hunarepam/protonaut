package protonaut.service.greeting;

import io.micronaut.context.annotation.Requires;

import javax.inject.Singleton;

@Singleton
//@Requires(missingProperty="app.country")
public class DefaultGreetingService implements GreetingService {
    @Override
    public String greet() {
        return "Default conveys you greeting";
    }
}
