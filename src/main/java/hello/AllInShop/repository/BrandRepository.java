package hello.AllInShop.repository;

import hello.AllInShop.domain.Brand;
import hello.AllInShop.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long>, QuerydslPredicateExecutor<Brand> {

    @Query("select b from Brand b where b.id =:id")
    Brand findId(@Param("id") Long id);

    @Query("select b from Brand b where b.brandName =:brandName")
    Brand findName(@Param("brandName") String brandName);

    List<Brand> findAllByOrderByBrandNameAsc();
}
