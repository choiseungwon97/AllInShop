package hello.AllInShop.dto;

import hello.AllInShop.domain.Brand;
import hello.AllInShop.domain.Category;
import hello.AllInShop.domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDTO {

    private Long id;

    private String name;

    private Gender gender;

    private int price;

    private int stock;

    private Long memberId; //작성자 아이디

    private String nickName; //작성자(관리자) 닉네임

    private Long brandId;

    private String brandName;

    private Long cateId;

    private String cateName;

    private int replyCount;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

}
