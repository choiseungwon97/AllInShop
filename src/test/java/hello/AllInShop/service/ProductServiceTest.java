package hello.AllInShop.service;

import hello.AllInShop.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static hello.AllInShop.domain.Gender.남성;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testRegister() {
        ProductDTO productDTO = ProductDTO.builder()
                .name("DtoNam")
                .gender(남성)
                .stock(100)
                .price(1000)
                .build();

        System.out.println(productService.register(productDTO));
    }
}