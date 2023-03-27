package hello.AllInShop.repository;

import hello.AllInShop.domain.ProductImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductImgRepositoryTest {

    @Autowired
    private ProductImgRepository productImgRepository;


    /**
     * 이미지 삭제 로직 확인
     */
    @Test
    @Transactional
    public void deleteImg() {

        Long id = 106L;

        productImgRepository.deleteById(id);

    }

    /**
     * 상품 이미지 단건 조회 로직 확인
     */
    @Test
    public void selectImg() {

        Long id = 109L;

        List<Object[]> byProductId = productImgRepository.findByProductId(id);

        for (Object[] objects : byProductId) {
            System.out.println("objects = " + objects);
        }
    }

}