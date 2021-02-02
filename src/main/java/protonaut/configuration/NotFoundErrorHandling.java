package protonaut.configuration;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import protonaut.exception.NotFoundException;

import javax.inject.Singleton;

@Produces
@Singleton
public class NotFoundErrorHandling implements ExceptionHandler<NotFoundException, HttpResponse> {

    @Override
    public HttpResponse handle(HttpRequest request, NotFoundException exception) {
        return HttpResponse.notFound(exception.getLocalizedMessage());
    }
}
