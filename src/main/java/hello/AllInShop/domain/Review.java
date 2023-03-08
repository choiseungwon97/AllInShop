package hello.AllInShop.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"product","member"})
public class Review extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private int grade;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
