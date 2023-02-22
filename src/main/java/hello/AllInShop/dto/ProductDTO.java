package hello.AllInShop.dto;

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

    //private String brandName;

    //일단보류
    //private String cateName;

    private LocalDateTime regDate, modDate;

}
