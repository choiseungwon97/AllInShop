package hello.AllInShop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    //review id
    private Long reviewId;

    //product id
    private Long pid;

    //member id
    private Long mid;

    //member nickname
    private String nickname;

    //member email
    private String email;

    private int grade;

    private String text;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
