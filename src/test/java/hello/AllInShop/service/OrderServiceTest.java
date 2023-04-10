package hello.AllInShop.service;

import hello.AllInShop.domain.Order;
import hello.AllInShop.domain.OrderStatus;
import hello.AllInShop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired OrderRepository orderRepository;
    @Autowired OrderService orderService;
    @Test
    public void 상품주문() throws Exception {

        int orderCount = 2;

        Long orderId = orderService.register(107L,100L,orderCount);

        Optional<Order> getOrder = orderRepository.findById(orderId);

        System.out.println("getOrder = " + getOrder);

    }

    @Test
    public void 상품주문_재고초과() throws Exception {

        int orderCount = 101;

        Long orderId = orderService.register(107L,100L,orderCount);

        Optional<Order> getOrder = orderRepository.findById(orderId);

        System.out.println("getOrder = " + getOrder);

    }
    @Test
    public void 주문취소() throws Exception {


        orderService.cancelOrder(16L);
    }


}