package hello.AllInShop.service;

import hello.AllInShop.domain.Product;
import hello.AllInShop.domain.Reply;
import hello.AllInShop.dto.ReplyDTO;

import java.util.List;

public interface ReplyService {

    //댓글 등록
    Long register(ReplyDTO replyDTO);

    //특정 상품 댓글 목록
    List<ReplyDTO> getList(Long id);

    //댓글 수정
    void modify(ReplyDTO replyDTO);

    //댓글 삭제
    void remove(Long id);

    //ReplyDTO를 ReplyEntity객체로 변환
    //Product 객체의 처리가 수반됨
    default Reply dtoToEntity(ReplyDTO replyDTO) {

        Product product = Product.builder().id(replyDTO.getProductId()).build();

        Reply reply = Reply.builder()
                        .id(replyDTO.getId())
                        .text(replyDTO.getText())
                        .replyer(replyDTO.getReplyer())
                        .product(product)
                        .build();
        return reply;
    }

    //ReplyEntity객체를 ReplyDTO로 변환
    //Product의 id만 필요
    default ReplyDTO entityToDTO(Reply reply) {
        ReplyDTO dto = ReplyDTO.builder()
                .id(reply.getId())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();
        return dto;
    }
}
