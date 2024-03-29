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
    private Long orderId;

    private LocalDateTime orderDate;

    private OrderStatus orderStatus;

    //delivery entity
    private Long deliveryId;




    //orderProduce entity

    //member entity
}
