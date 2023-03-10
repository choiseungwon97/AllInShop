package hello.AllInShop.controller;

import hello.AllInShop.dto.ReviewDTO;
import hello.AllInShop.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 한 상품에 대한 전체 리뷰 리스트
     * @param pid
     * @return
     */
    @GetMapping("/{pid}/all")
    public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("pid") Long pid) {

        log.info("=======================Review List=========================");
        log.info("pid: " + pid );

        List<ReviewDTO> reviewDTOList = reviewService.getListOfProduct(pid);

        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
    }

    /**
     * 상품 리뷰 등록
     * @param productReviewDTO
     * @return
     */
    @PostMapping("/{pid}")
    public ResponseEntity<Long> addReview (@RequestBody ReviewDTO productReviewDTO) {

        log.info("==================add Product Review===================");
        log.info("reviewDTO: " + productReviewDTO);

        Long id = reviewService.register(productReviewDTO);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    /**
     * 상품 리뷰 수정
     * @param reviewId
     * @param productReviewDTO
     * @return
     */
    @PutMapping("/{pid}/{reviewId}")
    public ResponseEntity<Long> modifyReview(@PathVariable Long reviewId, @RequestBody ReviewDTO productReviewDTO) {

        log.info("======================modify Product Review===================");
        log.info("reviewDTO: " + productReviewDTO);

        reviewService.modify(productReviewDTO);

        return new ResponseEntity<>(reviewId, HttpStatus.OK);

    }

    /**
     * 상품 리뷰 삭제
     * @param reviewId
     * @return
     */
    @DeleteMapping("/{pid}/{reviewId}")
    public ResponseEntity<Long> removeReview(@PathVariable Long reviewId) {

        log.info("================delete Product Review =====================");
        log.info("reviewId: " + reviewId);

        reviewService.remove(reviewId);

        return new ResponseEntity<>(reviewId, HttpStatus.OK);
    }
}
