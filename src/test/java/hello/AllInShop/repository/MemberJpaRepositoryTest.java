package hello.AllInShop.repository;

import hello.AllInShop.domain.Address;
import hello.AllInShop.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberJpaRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMembers() {
        IntStream.rangeClosed(1,100).forEach(i -> {

            Member member = Member.builder()
                    .email("user"+i +"@aaa.com")
                    .password("1111")
                    .nickname("USER"+i)
                    .address(new Address("서울시","경수대로",111))
                    .build();

            memberRepository.save(member);
        });
    }
}
