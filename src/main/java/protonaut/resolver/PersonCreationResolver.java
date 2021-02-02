package protonaut.resolver;

import io.micronaut.http.client.annotation.Client;
import io.micronaut.websocket.RxWebSocketClient;
import io.micronaut.websocket.WebSocketBroadcaster;
import io.micronaut.websocket.WebSocketSession;
import protonaut.client.UserWebSocketClient;
import protonaut.model.WebSocketMessage;
import protonaut.service.PersonService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.function.Predicate;

@Singleton
public class PersonCreationResolver {

    @Inject
    private PersonService personService;
    @Inject
    @Client("http://localhost:8080")
    RxWebSocketClient webSocketClient;
    @Inject
    private WebSocketBroadcaster broadcaster;


    public void resolvePersonCreation(String username, WebSocketSession session) {
        personService.createDefaultPersonByUsername(username);
        /*UserWebSocketClient userWebSocketClient =
                webSocketClient.connect(UserWebSocketClient.class, String.format("/user/%s", username))
                .blockingFirst();*/
        broadcaster.broadcastAsync("Person Created", isValid(username, session));
    }

    private Predicate<WebSocketSession> isValid(String username, WebSocketSession session) {
        return s -> username.equalsIgnoreCase(s.getUriVariables().get("username", String.class, null));
    }
}
