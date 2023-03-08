package hello.AllInShop.repository;

import hello.AllInShop.domain.Address;
import hello.AllInShop.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberJpaRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * member 테스트 데이터 생성
     */
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

    /**
     * member 삭제 시 리뷰 삭제
     */
    @Transactional
    @Commit
    @Test
    public void testDeleteMember() {

        Long id = 2L;

        Member member = Member.builder().id(id).build();

        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(id);
    }
}
