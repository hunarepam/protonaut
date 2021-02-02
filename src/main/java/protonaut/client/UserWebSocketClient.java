package protonaut.client;

import io.micronaut.http.HttpRequest;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.ClientWebSocket;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import protonaut.model.User;
import protonaut.model.WebSocketMessage;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;

@ClientWebSocket("/user/{username}")
public class UserWebSocketClient implements AutoCloseable {

    private WebSocketSession session;
    private HttpRequest request;
    private String username;
    private Collection<String> replies = new ConcurrentLinkedQueue<>();

    @OnOpen
    public void onOpen(String username, WebSocketSession session, HttpRequest request) {
        this.username = username;
        this.session = session;
        this.request = request;
    }

    public String getUsername() {
        return username;
    }

    public Collection<String> getReplies() {
        return replies;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public HttpRequest getRequest() {
        return request;
    }

    @OnMessage
    public void onMessage(
            String message) {
        replies.add(message);
    }

    public Future<WebSocketMessage> sendAsync(WebSocketMessage message) {
        return session.sendAsync(message);
    }


    @Override
    public void close() throws Exception {

    }
}
