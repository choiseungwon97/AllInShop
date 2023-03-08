package hello.AllInShop.repository;

import hello.AllInShop.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImgRepository extends JpaRepository<ProductImage, Long> {
}
