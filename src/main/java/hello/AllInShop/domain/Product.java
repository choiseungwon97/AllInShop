package hello.AllInShop.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
public class Product extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    private String name;

    private String category;

    private String brand;

    private String gender;

    private int price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Size> sizes = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<Heart> hearts = new ArrayList<>();

}
