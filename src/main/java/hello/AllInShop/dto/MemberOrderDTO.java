package hello.AllInShop.dto;

import hello.AllInShop.domain.DeliveryStatus;
import hello.AllInShop.domain.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class MemberOrderDTO {

    private String nickname;
    private Long orderId;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private int count;
    private String productName;
    private DeliveryStatus deliveryStatus;

}
