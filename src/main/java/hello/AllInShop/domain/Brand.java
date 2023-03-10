package hello.AllInShop.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"id","brandName"})
public class Brand extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id;

    private String brandName;

    @OneToMany(mappedBy = "brand")
    private List<Product> products = new ArrayList<>();

    public Brand(String brandName) {
        this.brandName = brandName;
    }
}
