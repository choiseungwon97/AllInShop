package hello.AllInShop.service;

import hello.AllInShop.domain.Member;
import hello.AllInShop.domain.Product;
import hello.AllInShop.domain.Review;
import hello.AllInShop.dto.ProductDTO;
import hello.AllInShop.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {

    //상품의 모든 상품리뷰를 가져온다.
    List<ReviewDTO> getListOfProduct(Long pid);

    //상품 리뷰 추가
    Long register(ReviewDTO productReviewDTO);

    //특정한 상품리뷰 수정
    void modify(ReviewDTO productReviewDTO);

    //상품 리뷰 삭제
    void remove(Long reviewId);

    default Review dtoToEntity(ReviewDTO productReviewDTO) {

        Review productReview = Review.builder().
                id(productReviewDTO.getReviewId())
                .product(Product.builder().id(productReviewDTO.getPid()).build())
                .member(Member.builder().id(productReviewDTO.getMid()).build())
                .grade(productReviewDTO.getGrade())
                .text(productReviewDTO.getText())
                .build();

        return productReview;
    }

    default ReviewDTO entityToDto(Review productReview) {

        ReviewDTO productReviewDTO = ReviewDTO.builder()
                .reviewId(productReview.getId())
                .pid(productReview.getProduct().getId())
                .mid(productReview.getMember().getId())
                .nickname(productReview.getMember().getNickname())
                .email(productReview.getMember().getEmail())
                .grade(productReview.getGrade())
                .text(productReview.getText())
                .regDate(productReview.getRegDate())
                .modDate(productReview.getModDate())
                .build();

        return productReviewDTO;
    }
}
