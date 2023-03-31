package hello.AllInShop.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "order_product")
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
public class OrderProduct extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "order_product_id")
    private Long id;

    @Column(name = "order_price")
    private int orderPrice;

    private int count;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    //==생성 메서드==
    public static OrderProduct createOrderProduct(Product product, int orderPrice, int count) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setOrderPrice(orderPrice);
        orderProduct.setCount(count);

        product.removeStock(count);
        return orderProduct;
    }

    //==비즈니스 로직==
    //주문 취소
    public void cancel() {
        getProduct().addStock(count);
    }

    //==비즈니스 로직==
    //주문상품 전체 가격 조회
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
