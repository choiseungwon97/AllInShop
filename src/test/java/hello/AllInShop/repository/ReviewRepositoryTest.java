package hello.AllInShop.repository;

import hello.AllInShop.domain.Member;
import hello.AllInShop.domain.Product;
import hello.AllInShop.domain.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMovieReviews() {

        //200개의 리뷰를 등록
        IntStream.rangeClosed(1,200).forEach(i -> {

            //상품 번호
            Long Product_id = (long)(Math.random()*100 +1);
            Product product = Product.builder().id(Product_id).build();

            //리뷰어 번호
            Long Member_id = (long)(Math.random()*100 +1);
            Member member = Member.builder().id(Member_id).build();

            Review productReview = Review.builder()
                    .member(member)
                    .product(product)
                    .grade((int) (Math.random() * 5) + 1)
                    .text("이 영화에 대한 느낌...." + i)
                    .build();

            reviewRepository.save(productReview);
        });
    }

    /**
     * 특정 상품에 대한 모든 리뷰 정보
     */
    @Test
    public void testGetMovieReviews() {
        Product product = Product.builder().id(94L).build();

        List<Review> result = reviewRepository.findByProduct(product);

        result.forEach(productReview -> {

            System.out.println(productReview.getId());
            System.out.println(productReview.getGrade());
            System.out.println(productReview.getText());
            System.out.println(productReview.getMember().getEmail());
        });
    }

}