package protonaut.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.websocket.WebSocketBroadcaster;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.micronaut.websocket.annotation.ServerWebSocket;
import protonaut.model.Order;
import protonaut.model.WebSocketMessage;
import protonaut.resolver.OrderProcessingResolver;
import protonaut.service.OrderService;

import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

@ServerWebSocket("/order/{username}")
public class OrderWebSocketServer {

    private final WebSocketBroadcaster broadcaster;
    private final ObjectMapper objectMapper;
    private final OrderProcessingResolver orderProcessingResolver;
    private final OrderService orderService;

    public OrderWebSocketServer(WebSocketBroadcaster broadcaster,
                                ObjectMapper objectMapper,
                                OrderProcessingResolver orderProcessingResolver,
                                OrderService orderService) {
        this.broadcaster = broadcaster;
        this.objectMapper = objectMapper;
        this.orderProcessingResolver = orderProcessingResolver;
        this.orderService = orderService;
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
            Order order = objectMapper.readValue(message.getBody(), Order.class);
            Order submittedOrder = orderService.placeOrder(order);
            CompletableFuture.runAsync(() ->
                    orderProcessingResolver.submitOrder(username, submittedOrder.getId(), session));
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
