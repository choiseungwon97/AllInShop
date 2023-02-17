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
public class OrderProduct {

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
}
