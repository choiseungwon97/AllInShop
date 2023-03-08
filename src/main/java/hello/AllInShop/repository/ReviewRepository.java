package hello.AllInShop.repository;

import hello.AllInShop.domain.Member;
import hello.AllInShop.domain.Product;
import hello.AllInShop.domain.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * 특정 상품에 대한 모든 리뷰 정보
     */
    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByProduct(Product product);

    /**
     * member삭제시 review 삭제
     */
    @Modifying
    @Query("delete from Review r where r.member = :member")
    void deleteByMember(@Param("member") Member member);
}
