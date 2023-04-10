package hello.AllInShop.service;

import hello.AllInShop.domain.Address;

public interface OrderService {

    Long register (Long memberId, Long ProductId, int count, String city, String street, int zipcode);

    void cancelOrder(Long orderId);
}
