package hello.AllInShop.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
@ToString(of = {"id","name","gender","price","stock"})
public class Product extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int price;

    private int stock;

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
        this.category = category;
        category.getProducts().add(this);
    }

    public void changeBrand(Brand brand) {
        this.brand = brand;
        brand.getProducts().add(this);
    }
}
