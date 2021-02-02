package protonaut.controller;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class VersionControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testGreetingService() {
        String response = client.toBlocking()
                .retrieve(HttpRequest.GET("/greet/conditional"));
        assertEquals("Default conveys you greeting", response); //"Vitaju siabry"  "Hi folks!" "Default conveys you greeting"
    }
}