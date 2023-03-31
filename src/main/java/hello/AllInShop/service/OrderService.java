package hello.AllInShop.service;

public interface OrderService {

    Long register (Long memberId, Long ProductId, int count);

    void cancelOrder(Long orderId);
}
