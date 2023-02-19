package hello.AllInShop.domain;


import lombok.AccessLevel;
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
public class Member extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String nickname;

    @Embedded
    private Address address;

    private int grade;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Heart> hearts = new ArrayList<>();

}
