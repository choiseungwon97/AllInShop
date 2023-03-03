package hello.AllInShop.repository;

import hello.AllInShop.domain.Product;
import hello.AllInShop.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    //댓글 목록
    List<Reply> getRepliesByProductOrderById(Product product);

    //product 삭제시 관련된 댓글 삭제
    @Modifying
    @Query("delete from Reply r where r.product.id =:id")
    void deleteById(@Param("id") Long id);
}
