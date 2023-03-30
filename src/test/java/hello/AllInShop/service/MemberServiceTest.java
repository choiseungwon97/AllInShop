package hello.AllInShop.service;

import hello.AllInShop.dto.MemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;


    @Test
    public void 회원가입() {
        MemberDTO dto = MemberDTO.builder()
                .email("testService@aaa.com")
                .nickname("테스트지롱")
                .city("맨시티")
                .street("스트리트파이터")
                .zipcode(11111)
                .password("1212")
                .build();

        System.out.println("memberService = " + memberService.register(dto));
    }
}