package protonaut.service;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import protonaut.entity.OrderEntity;
import protonaut.exception.NotFoundException;
import protonaut.model.Order;
import protonaut.model.OrderStatus;
import protonaut.repository.OrderRepository;

import javax.inject.Singleton;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Singleton
public class OrderService {

    private final OrderHistoryService orderHistoryService;
    private final OrderRepository repository;
    private final MapperFacade mapper;

    public OrderService(OrderHistoryService orderHistoryService,
                        OrderRepository repository, MapperFactory mapperFactory) {
        this.orderHistoryService = orderHistoryService;
        this.repository = repository;
        this.mapper = mapperFactory.getMapperFacade();
    }

    public Order placeOrder(@Valid Order order) {
        order.setStatus(OrderStatus.PLACED.getDescription());
        OrderEntity entity = mapper.map(order, OrderEntity.class);
        entity = repository.saveAndFlush(entity);
        Order placedOrder = mapper.map(entity, Order.class);
        orderHistoryService.add(placedOrder);
        return placedOrder;
    }

    public Order getById(Long id) {
        Optional<OrderEntity> existedOrder = repository.findById(id);
        if (existedOrder.isPresent()) {
            return mapper.map(existedOrder.get(), Order.class);
        }
        throw new NotFoundException(String.format("Order %s not found", id));
    }

    public String submitOrder(Long id) {
        repository.updateStatus(id, OrderStatus.SUBMITTED.getDescription());

        return OrderStatus.SUBMITTED.getDescription();
    }

    public List<Order> getOrderHistory() {
        return orderHistoryService.getOrders();
    }
}
