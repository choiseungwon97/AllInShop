package hello.AllInShop.repository;

import hello.AllInShop.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {

    //한개의 로우(Object) 내에 Object[]로 나옴
    @Query("select p,w from Product p left join p.writer w where p.id =:id")
    Object getProductWithWriter(@Param("id") Long id);

    @Query("select p,r from Product p left join Reply r on r.product = p " +
            "where p.id =:id")
    List<Object[]> getProductWithReply(@Param("id") Long id);

    @Query(value = "select p, b, c, w, count(r) from Product p left join p.writer w " +
            " left join p.brand b left join p.category c left join Reply r on r.product = p" +
            " group by p", countQuery = "select count(p) from Product p")
    Page<Object[]> getProductWithReplyCount(Pageable pageable);

    @Query(value = "select p, b, c, w, count(r) from Product p left join p.writer w " +
            " left join p.category c left join p.brand b" +
            " left join Reply r on r.product = p " +
            " where p.id =:id")
    Object getProductById(@Param("id") Long id);

    @Query("select p,w,c,b from Product p left join p.writer w left join p.category c" +
            " left join p.brand b where p.id =:id")
    Object getProductWithWriterBrandCate(@Param("id") Long id);


}
