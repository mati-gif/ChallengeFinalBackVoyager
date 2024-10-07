//package mindhub.VoyagerRestaurante.ServicesTests;
//
//import mindhub.VoyagerRestaurante.models.Order;
//import mindhub.VoyagerRestaurante.repositories.OrderRepository;
//import mindhub.VoyagerRestaurante.services.implementation.OrderServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class OrderServiceTest {
//
//    @Mock
//    private OrderRepository orderRepository;
//
//    @InjectMocks
//    private OrderServiceImpl orderService;
//
//    private Order order;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        order = new Order(LocalDateTime.now(), 50.0, null);
//    }
//
//    @Test
//    void testSaveOrder() {
//        when(orderRepository.save(any(Order.class))).thenReturn(order);
//
//        Order savedOrder = orderService.saveOrder(order);
//        assertEquals(order.getTotalAmount(), savedOrder.getTotalAmount());
//        verify(orderRepository, times(1)).save(order);
//    }
//
//    @Test
//    void testGetOrderById() {
//        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
//
//        Optional<Order> foundOrder = orderService.getOrderById(1L);
//        assertEquals(order.getTotalAmount(), foundOrder.get().getTotalAmount());
//        verify(orderRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void testDeleteOrder() {
//        orderService.deleteOrder(1L);
//        verify(orderRepository, times(1)).deleteById(1L);
//    }
//}
//
