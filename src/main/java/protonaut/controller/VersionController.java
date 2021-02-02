package protonaut.controller;

import io.micronaut.core.version.annotation.Version;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import protonaut.service.greeting.GreetingService;

@Controller("/greet")
public class VersionController {

    private final GreetingService greetingService;

    public VersionController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @Get
    @Version("1")
    public String deprecatedGreet() {
        return "OldGreeting";
    }

    @Get
    @Version("2")
    public HttpResponse<String> greet() {
        return HttpResponse.ok("greeting");
    }

    @Get(value = "/conditional", produces = MediaType.APPLICATION_JSON)
    public String conitionallyGreet() {
        return greetingService.greet();
    }

}
