package protonaut.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import protonaut.model.Order;
import protonaut.service.OrderHistoryService;
import protonaut.service.OrderService;

import java.util.List;

@Controller("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderHistoryService orderHistoryService;

    public OrderController(OrderService orderService, OrderHistoryService orderHistoryService) {
        this.orderService = orderService;
        this.orderHistoryService = orderHistoryService;
    }

    @Status(HttpStatus.CREATED)
    @Post
    public Order create(@Body Order order) {
        return orderService.placeOrder(order);
    }

    @Get("/{orderId}")
    public HttpResponse<Order> getUserById(@PathVariable Long orderId) {
        return HttpResponse.ok(orderService.getById(orderId));
    }

    @Get("/history")
    public List<Order> getOrderHistory() {
        return orderHistoryService.getOrders();
    }
}
