package hello.AllInShop.service;

import hello.AllInShop.domain.Product;
import hello.AllInShop.domain.Reply;
import hello.AllInShop.dto.ProductDTO;
import hello.AllInShop.dto.ReplyDTO;
import hello.AllInShop.repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static hello.AllInShop.domain.Gender.남성;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyServiceTest {

    @Autowired
    private ReplyService replyService;
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void testGetList() {
        Long id = 97L;

        List<ReplyDTO> list = replyService.getList(id);

        for (ReplyDTO dto : list) {
            System.out.println("dto = " + dto);
        }
    }



    @Test
    public void testResiterV2() {

        Product product = Product.builder().id(97L).build();

        Reply reply = Reply.builder()
                .id(301L)
                .text("Gd")
                .replyer("ㅇㄴㅇㄴㅁ")
                .product(product)
                .build();


        ReplyDTO dto = ReplyDTO.builder()
                .text("gggg")
                .replyer("ggd")
                .productId(97L)
                .build();

        System.out.println(replyService.register(dto));
    }
}