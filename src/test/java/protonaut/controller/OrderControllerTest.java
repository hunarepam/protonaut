package protonaut.controller;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import protonaut.model.Order;
import protonaut.model.OrderStatus;
import protonaut.service.OrderService;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@MicronautTest
public class OrderControllerTest {

    @Inject
    OrderService orderService;

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void createOrder() {
        Order order = new Order();
        order.setDeliveryAddress("praspiekt Kalinouskaha 117a - 4");
        order.setStatus(OrderStatus.PLACED.getDescription());
        order.setAmount(1863);

        when(orderService.placeOrder(any(Order.class))).then(i -> i.getArguments()[0]);

        Order actual = client.toBlocking()
                .retrieve(HttpRequest.POST("/orders", order), Order.class);

        assertEquals(order, actual);

        verify(orderService).placeOrder(order);
        verifyNoMoreInteractions(orderService);
    }

    @MockBean(value = OrderService.class)
    OrderService orderServiceMock() {
        return mock(OrderService.class);
    }
}
