package hello.AllInShop.service;

import hello.AllInShop.domain.Product;
import hello.AllInShop.dto.PageRequestDTO;
import hello.AllInShop.dto.PageResultDTO;
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

    @Test
    public void testSearch() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(3).size(10).type("g").keyword("남성").build();

        PageResultDTO<ProductDTO, Product> resultDTO = productService.getList(pageRequestDTO);

        System.out.println("resultDTO.isPrev() = " + resultDTO.isPrev());
        System.out.println("resultDTO.isNext() = " + resultDTO.isNext());
        System.out.println("resultDTO.getTotalPage() = " + resultDTO.getTotalPage());

        System.out.println(" ================================================ ");

        for (ProductDTO productDTO : resultDTO.getDtoList()) {
            System.out.println("productDTO = " + productDTO);
        }

        System.out.println(" ================================================ ");
        resultDTO.getPageList().forEach(i -> System.out.println("i = " + i));
    }


}