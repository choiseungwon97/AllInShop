package hello.AllInShop.repository;

import hello.AllInShop.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductImgRepository extends JpaRepository<ProductImage, Long> {

    /**
     * product 삭제시 관련된 product img 삭제
     */
    @Modifying
    @Query("delete from ProductImage pi where pi.product.id =:id")
    void deleteByProductId(@Param("id") Long id);

    /**
     * product 수정 시 해당 product img 조회
     */
    @Query("select pi from ProductImage pi where pi.product.id =:id")
    List<Object[]> findByProductId(@Param("id") Long id);

    /**
     * product img 단건 삭제
     */
    @Transactional
    @Modifying
    @Query("delete from ProductImage pi where pi.uuid =:uuid")
    void deleteByUuid(@Param("uuid") String uuid);
}
