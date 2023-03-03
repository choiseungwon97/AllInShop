package hello.AllInShop.repository;

import hello.AllInShop.domain.Product;
import hello.AllInShop.domain.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyJpaRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReply() {

        IntStream.rangeClosed(1,300).forEach( i -> {
            long id = (long) (Math.random() * 100) + 1;

            Product product = Product.builder().id(id).build();

            Reply reply = Reply.builder()
                    .text("Reply ========="+i)
                    .product(product)
                    .replyer("guest").build();

            replyRepository.save(reply);
        });
    }
    
    @Test
    public void readReply1() {
        Optional<Reply> result = replyRepository.findById(1L);
        
        Reply reply = result.get();

        System.out.println("reply = " + reply);
        System.out.println("reply.getProduct() = " + reply.getProduct());
    }

    @Test
    public void testListByProduct() {
        List<Reply> replyList = replyRepository.getRepliesByProductOrderById(
                                    Product.builder().id(97L).build());

        for (Reply reply : replyList) {
            System.out.println(reply);
        }
    }
}
