package hello.AllInShop.service;

import hello.AllInShop.domain.*;
import hello.AllInShop.dto.MemberOrderDTO;
import hello.AllInShop.dto.PageRequestDTO;
import hello.AllInShop.dto.PageResultDTO;

public interface OrderService {

    Long register (Long memberId, Long ProductId, int count, String city, String street, int zipcode);

    void cancelOrder(Long orderId);

    /**
     * 한 고객의 주문 조회 페이징 버전
     */
    PageResultDTO<MemberOrderDTO, Object[]> getList(Long id, PageRequestDTO pageRequestDTO);

    default MemberOrderDTO entityToDto(Member member, Order order, OrderProduct orderProduct, Product product, Delivery delivery) {
        MemberOrderDTO memberOrderDTO = MemberOrderDTO.builder()
                .nickname(member.getNickname())
                .orderId(order.getId())
                .orderStatus(order.getStatus())
                .orderDate(order.getOrderDate())
                .count(orderProduct.getCount())
                .productName(product.getName())
                .deliveryStatus(delivery.getStatus())
                .build();

        return memberOrderDTO;
    }
}
