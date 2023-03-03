package hello.AllInShop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyDTO {

    private Long id;

    private String text;

    private String replyer;

    private Long productId;

    private LocalDateTime regDate;

    private LocalDateTime modDate;
}
