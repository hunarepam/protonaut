package protonaut.service;

import io.micronaut.runtime.context.scope.Refreshable;
import protonaut.model.Order;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Refreshable
public class OrderHistoryService {

    private List<Order> orders;

    @PostConstruct
    public void init() {
        orders = new ArrayList<>();
    }

    public void add(Order order) {
        orders.add(order);
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }
}
