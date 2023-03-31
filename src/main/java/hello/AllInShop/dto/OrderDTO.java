package hello.AllInShop.dto;

import hello.AllInShop.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO {

    //order entity
    private Long id;

    private LocalDateTime orderDate;

    private OrderStatus status;

    //delivery entity


    //orderProduce entity

    //member entity
}
