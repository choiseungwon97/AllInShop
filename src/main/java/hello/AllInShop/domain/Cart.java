package hello.AllInShop.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
public class Cart {

    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private Long id;

    private int count;
}
