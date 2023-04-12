package hello.AllInShop.repository;

import hello.AllInShop.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {


    /**
     * 한 고객의 주문 조회
     */
    @Query("select m.nickname, o.status, o.id,o.orderDate, op.count, p.name, d.status from Order o " +
            "inner join Member m on o.member.id = m.id " +
            "inner join OrderProduct op on o.id = op.order.id " +
            "inner join Product p on p.id = op.product.id " +
            "inner join Delivery d on o.delivery.id = d.id " +
            "where o.member.id = :id")
    List<Object[]> findByOneMemberOrder (@Param("id") Long id);


    /**
     * 한 고객의 주문 조회 페이징 버전
     * 위에처럼 필요한 것만 안받고 전체 받는 이유: 1. 화면에 추가할게 있으면 dto에서 추가 시키면 편함
     * 2. o.id, o.status 등등 세부적으로 캐스팅하면 복잡함 -> 그냥 Order 하나로 캐스팅
     */
    @Query("select m, o, op, p, d from Order o " +
            "inner join Member m on o.member.id = m.id " +
            "inner join OrderProduct op on o.id = op.order.id " +
            "inner join Product p on p.id = op.product.id " +
            "inner join Delivery d on o.delivery.id = d.id " +
            "where o.member.id = :id")
    Page<Object[]> findByOneMemberOrderPage (@Param("id") Long id, Pageable pageable);

}
