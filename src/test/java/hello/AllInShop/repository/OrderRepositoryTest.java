package hello.AllInShop.repository;

import hello.AllInShop.domain.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    @Test
    public void 한고객_주문조회() {

        Long id = 90L;

        Pageable pageable = PageRequest.of(0,10, Sort.by("id").descending());

        Page<Object[]> member = orderRepository.findByOneMemberOrderPage(id, pageable);

        member.get().forEach(row -> {
            Object[] arr = (Object[]) row;
            System.out.println("arr = " + Arrays.toString(arr));
        });
    }

}