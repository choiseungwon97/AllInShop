package hello.AllInShop.service;

import hello.AllInShop.domain.Product;
import hello.AllInShop.domain.Review;
import hello.AllInShop.dto.ReviewDTO;
import hello.AllInShop.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewDTO> getListOfProduct(Long pid) {

        Product product = Product.builder().id(pid).build();

        List<Review> result = reviewRepository.findByProduct(product);

        return result.stream().map(productReview -> entityToDto(productReview)).collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDTO productReviewDTO) {

        Review productReview = dtoToEntity(productReviewDTO);

        reviewRepository.save(productReview);

        return productReview.getId();
    }

    @Override
    public void modify(ReviewDTO productReviewDTO) {

        Optional<Review> result = reviewRepository.findById(productReviewDTO.getReviewId());

        if(result.isPresent()) {

            Review productReview = result.get();
            productReview.changeGrade(productReviewDTO.getGrade());
            productReview.changeText(productReviewDTO.getText());

            reviewRepository.save(productReview);
        }
    }

    @Override
    public void remove(Long reviewId) {

        reviewRepository.deleteById(reviewId);
    }
}
