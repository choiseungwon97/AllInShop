package hello.AllInShop.service;

import hello.AllInShop.domain.*;
import hello.AllInShop.dto.MemberOrderDTO;
import hello.AllInShop.dto.PageRequestDTO;
import hello.AllInShop.dto.PageResultDTO;
import hello.AllInShop.dto.ProductDTO;
import hello.AllInShop.repository.MemberRepository;
import hello.AllInShop.repository.OrderRepository;
import hello.AllInShop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    /**
     * 주문
     */
    @Override
    public Long register(Long memberId, Long productId, int count,  String city, String street, int zipcode) {


        //엔티티 조회
        Optional<Member> member = memberRepository.findById(memberId);
        Optional<Product> product = productRepository.findById(productId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(new Address(city, street, zipcode));
        delivery.setStatus(DeliveryStatus.READY);

        //주문상품 생성
        OrderProduct orderProduct = OrderProduct.createOrderProduct(product.get(), product.get().getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member.get(), delivery, orderProduct);

               //주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 취소
     */
     public void cancelOrder(Long orderId) {

         //주문 엔티티 조회
         Optional<Order> order = orderRepository.findById(orderId);

         //주문 취소
         order.get().cancel();
     }

    @Override
    public PageResultDTO<MemberOrderDTO, Object[]> getList(Long id, PageRequestDTO pageRequestDTO) {

         log.info(String.valueOf(pageRequestDTO));


        Function<Object[], MemberOrderDTO> fn =
                (en -> entityToDto(
                        (Member) en[0],
                        (Order) en[1],
                        (OrderProduct) en[2],
                        (Product) en[3],
                        (Delivery) en[4]

                ));


        log.info(String.valueOf((fn)));

        Page<Object[]> result = orderRepository.findByOneMemberOrderPage(id, pageRequestDTO.getPageable(Sort.by("id").descending()));

        return new PageResultDTO<>(result, fn);

    }


}
