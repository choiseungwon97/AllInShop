package hello.AllInShop.controller;

import hello.AllInShop.dto.ReplyDTO;
import hello.AllInShop.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/replies")
@Slf4j
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping(value = "/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByProduct(@PathVariable("id") Long id) {

        log.info("id: " + id);

        return new ResponseEntity<>(replyService.getList(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO) {

        log.info(String.valueOf(replyDTO));

        Long id = replyService.register(replyDTO);

        return new ResponseEntity<>(id,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modify (@RequestBody ReplyDTO replyDTO) {

        log.info(String.valueOf(replyDTO));

        replyService.modify(replyDTO);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") Long id) {

        log.info("id:" + id);

        replyService.remove(id);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
