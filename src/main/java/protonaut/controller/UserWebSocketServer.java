package protonaut.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.websocket.WebSocketBroadcaster;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.micronaut.websocket.annotation.ServerWebSocket;
import protonaut.model.User;
import protonaut.model.WebSocketMessage;
import protonaut.resolver.PersonCreationResolver;
import protonaut.service.UserService;

import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

@ServerWebSocket("/user/{username}")
public class UserWebSocketServer {
    private final WebSocketBroadcaster broadcaster;
    private final UserService userService;
    private final PersonCreationResolver personCreationResolver;
    private final ObjectMapper objectMapper;

    public UserWebSocketServer(WebSocketBroadcaster broadcaster,
                               UserService userService,
                               PersonCreationResolver personCreationResolver,
                               ObjectMapper objectMapper) {
        this.broadcaster = broadcaster;
        this.userService = userService;
        this.personCreationResolver = personCreationResolver;
        this.objectMapper = objectMapper;
    }

    @OnOpen
    public void onOpen(String username, WebSocketSession session) {
        String msg = "[" + username + "] Joined!";
        broadcaster.broadcastSync(msg, isValid(username, session));
    }

    @OnMessage
    public CompletableFuture<String> onMessage(
            String username,
            WebSocketMessage message,
            WebSocketSession session) throws JsonProcessingException {
        if ("CREATE".equals(message.getAction())) {
            User user = objectMapper.readValue(message.getBody(), User.class);
            userService.create(user);
            CompletableFuture.runAsync(() -> personCreationResolver.resolvePersonCreation(username, session));
        }
        return broadcaster.broadcastAsync(message.getBody(), isValid(username, session));
    }

    @OnClose
    public void onClose(String username, WebSocketSession session) {
        String msg = "[" + username + "] Disconnected!";
        broadcaster.broadcastSync(msg, isValid(username, session));
    }

    private Predicate<WebSocketSession> isValid(String username, WebSocketSession session) {
        return s -> username.equalsIgnoreCase(s.getUriVariables().get("username", String.class, null));
    }
}
