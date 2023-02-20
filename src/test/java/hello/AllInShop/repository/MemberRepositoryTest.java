package hello.AllInShop.repository;

import hello.AllInShop.domain.Address;
import hello.AllInShop.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void before() {
        Member memberA = new Member(
                "test1@test.com",
                "Admin",
                "1234",
                new Address("서울","여의도", 123),
                1);

        Member memberB = new Member(
                "test2@test.com",
                "user",
                "1234",
                new Address("서울","여의도", 123),
                null);
        em.persist(memberA);
        em.persist(memberB);
    }

    /**
     * Member 생성
     */
    @Test
    @Rollback
    public void insertMember() {
        Member memberA = new Member(
                "test1@test.com",
                "Admin",
                "1234",
                new Address("서울","여의도", 123),
                1);

        Member memberB = new Member(
                "test2@test.com",
                "user",
                "1234",
                new Address("서울","여의도", 123),
                null);

        Member savedMemberA = memberRepository.save(memberA);
        Member savedMemberB = memberRepository.save(memberB);
    }

    @Test
    public void updateMember() {
        Optional<Member> result = memberRepository.findByEmail("test1@test.com");

        if (result != null) {

            Member member = result.get();

            member.UpdateNickname("UpdateAdmin");
            member.UpdatePassword("UpdatePassword");
            member.UpdateAddress(new Address("updateCity", "updateStreet", 123));

            memberRepository.save(member);

        }

    }

}