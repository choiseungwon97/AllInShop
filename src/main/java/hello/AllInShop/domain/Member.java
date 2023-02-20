package hello.AllInShop.domain;


import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
@ToString(of = {"id","email", "nickname", "password", "grade"})
public class Member extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String nickname;

    private String password;

    @Embedded
    private Address address;

    private Integer grade;

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
