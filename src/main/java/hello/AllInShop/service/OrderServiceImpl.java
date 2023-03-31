package hello.AllInShop.service;

import hello.AllInShop.domain.*;
import hello.AllInShop.repository.MemberRepository;
import hello.AllInShop.repository.OrderRepository;
import hello.AllInShop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    /**
     * 주문
     */
    @Override
    public Long register(Long memberId, Long productId, int count) {


        //엔티티 조회
        Optional<Member> member = memberRepository.findById(memberId);
        Optional<Product> product = productRepository.findById(productId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.get().getAddress());
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

}
