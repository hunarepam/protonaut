package protonaut.resolver;

import io.micronaut.websocket.WebSocketBroadcaster;
import io.micronaut.websocket.WebSocketSession;
import protonaut.model.OrderStatus;
import protonaut.service.OrderService;

import javax.inject.Singleton;
import java.util.function.Predicate;

@Singleton
public class OrderProcessingResolver {

    private final OrderService orderService;
    private final WebSocketBroadcaster broadcaster;

    public OrderProcessingResolver(OrderService orderService, WebSocketBroadcaster broadcaster) {
        this.orderService = orderService;
        this.broadcaster = broadcaster;
    }

    public void submitOrder(String username, Long orderId, WebSocketSession session) {
        orderService.submitOrder(orderId);
        broadcaster.broadcastSync(String.format("Order %s has been transferred into %s status",
                orderId, OrderStatus.SUBMITTED.getDescription()), isValid(username, session));
    }

    private Predicate<WebSocketSession> isValid(String username, WebSocketSession session) {
        return s -> username.equalsIgnoreCase(s.getUriVariables().get("username", String.class, null));
    }
}
