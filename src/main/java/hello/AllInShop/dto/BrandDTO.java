package hello.AllInShop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BrandDTO {
    private Long id;

    private String brandName;

    private LocalDateTime regDate, modDate;
}
