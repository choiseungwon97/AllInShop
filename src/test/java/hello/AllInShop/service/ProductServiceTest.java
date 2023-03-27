package hello.AllInShop.service;


import hello.AllInShop.dto.PageRequestDTO;
import hello.AllInShop.dto.PageResultDTO;
import hello.AllInShop.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static hello.AllInShop.domain.Gender.남성;


@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testRegister() {
        ProductDTO productDTO = ProductDTO.builder()
                .name("DtoName")
                .gender(남성)
                .stock(100)
                .price(1000)
                .build();

        System.out.println(productService.register(productDTO));
    }

    /*@Test
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
    }*/

    @Test
    public void testResiterV2() {
        ProductDTO productDTO = ProductDTO.builder()
                .name("DtoName123")
                .gender(남성)
                .stock(100)
                .price(1000)
                .memberId(5L)
                .cateId(5L)
                .brandId(5L)
                .build();

        System.out.println(productService.register(productDTO));
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<ProductDTO, Object[]> result = productService.getList(pageRequestDTO);

        for (ProductDTO productDTO : result.getDtoList()) {
            System.out.println(productDTO);
        }
    }

    @Test
    public void testRead() {
        Long id = 100L;

        ProductDTO productDTO = productService.read(id);

        System.out.println(productDTO);
    }

    /**
     * 상품 삭제 로직
     */
    @Test
    public void testRemove() {
        Long id = 108L;

        productService.removeWithReplies(id);
    }

    @Test
    public void testModify() {
        ProductDTO productDTO = ProductDTO.builder()
                .id(100L)
                .name("수정kkk")
                .gender(남성)
                .price(2000)
                .stock(2000)
                .brandName("수정브랜드kkk")
                .cateName("수정")
                .build();

        productService.modify(productDTO);
    }

}