package hello.AllInShop.service;

import hello.AllInShop.dto.MemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;


    @Transactional
    @Test
    public void 회원가입() {
        MemberDTO dto = MemberDTO.builder()
                .email("testService31@aaa.com")
                .nickname("테스트지롱")
                .city("맨시티")
                .street("스트리트파이터")
                .zipcode(11111)
                .password("1212")
                .build();

        memberService.register(dto);

    }

    @Transactional
    @Test
    public void 중복_회원가입() throws Exception {
        MemberDTO dto = MemberDTO.builder()
                .email("testService31@aaa.com")
                .nickname("테스트지롱")
                .city("맨시티")
                .street("스트리트파이터")
                .zipcode(11111)
                .password("1212")
                .build();

        MemberDTO dto2 = MemberDTO.builder()
                .email("testService31@aaa.com")
                .nickname("테스트지롱")
                .city("맨시티")
                .street("스트리트파이터")
                .zipcode(11111)
                .password("1212")
                .build();

        memberService.register(dto);
        memberService.register(dto2);


    }
}