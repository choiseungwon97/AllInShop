package hello.AllInShop.service;

import hello.AllInShop.domain.Product;
import hello.AllInShop.domain.Reply;
import hello.AllInShop.dto.ReplyDTO;
import hello.AllInShop.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;

    @Override
    public Long register(ReplyDTO replyDTO) {

        log.info("DTO..................................");
        log.info(String.valueOf(replyDTO));

        Reply reply = dtoToEntity(replyDTO);

        log.info(String.valueOf(reply));
        replyRepository.save(reply);

        return reply.getId();
    }

    @Override
    public List<ReplyDTO> getList(Long id) {

        List<Reply> result = replyRepository.getRepliesByProductOrderById(Product.builder().id(id).build());

        return result.stream().map(reply -> entityToDTO(reply)).collect(Collectors.toList());
    }

    @Override
    public void modify(ReplyDTO replyDTO) {

        Reply reply = dtoToEntity(replyDTO);

        replyRepository.save(reply);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        replyRepository.deleteOneById(id);
    }
}
