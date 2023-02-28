package hello.AllInShop.domain;


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
@ToString(of = {"id","email", "nickname", "password", "grade","address" })
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String nickname;

    private String password;

    @Embedded
    private Address address;

    private Integer grade;

    @OneToMany(mappedBy = "writer")
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Heart> hearts = new ArrayList<>();

    public Member(String email, String nickname, String password, Address address, Integer grade) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.address = address;
        if (grade == null) {
            this.grade = 0;
        } else {
            this.grade = grade;
        }
    }

    public void UpdateNickname(String nickname) {
        this.nickname = nickname;
    }
    public void UpdatePassword(String password) {
        this.password = password;
    }
    public void UpdateAddress(Address address) {
        this.address = address;
    }
}
