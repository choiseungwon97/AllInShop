package hello.AllInShop.domain;


import hello.AllInShop.exception.NotEnoughStockException;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"id","name","gender","price","stock"})
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int price;

    private int stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "product")
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> productImages = new ArrayList<>();

    public Product(String name, Gender gender, int price, int stock) {
        this.name = name;
        this.gender = gender;
        this.price = price;
        this.stock = stock;
    }

    public Product(String name, Gender gender, int price, int stock, Category category, Brand brand) {
        this.name = name;
        this.gender = gender;
        this.price = price;
        this.stock = stock;
        if (category != null) {
            changeCategory(category);
        }
        if (category != null) {
            changeBrand(brand);
        }
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeGender(Gender gender) {
        this.gender = gender;
    }

    public void changePrice(int price) {
        this.price = price;
    }
    public void changeStock(int stock) {
        this.stock = stock;
    }

    public void changeCategory(Category category) {
        //Product에 이미 Category가 설정 되어있을 경우
        if(this.category != null) {
            //category 에서 해당 Entity를 제거
            this.category.getProducts().remove(this);
        }
        //해당 product Entity에 파라미터로 들어온 category 연관 관계 설정
        this.category = category;

        //파라미터로 들어온 category Entity에 product 연관 관계 설정
        category.getProducts().add(this);
    }

    public void changeBrand(Brand brand) {
        if(this.brand != null) {
            this.brand.getProducts().remove(this);
        }
        this.brand = brand;

        brand.getProducts().add(this);
    }

    //==비즈니스 로직==
    //주문 취소시 재고 더하기
    public void addStock(int quantity) {
        this.stock += quantity;
    }

    //주문 시 재고 빼기
    public void removeStock(int quantity) {
        int restStock = this.stock - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("재고가 부족합니다.");
        }
        this.stock = restStock;
    }

}
