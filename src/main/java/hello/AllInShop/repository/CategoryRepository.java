package hello.AllInShop.repository;

import hello.AllInShop.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long>, QuerydslPredicateExecutor<Category> {

    @Query("select c from Category c where c.id =:id")
    Category findId(@Param("id") Long id);

    @Query("select c from Category c where c.cateName =:cateName")
    Category findName(@Param("cateName") String cateName);
}
