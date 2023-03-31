package hello.AllInShop.service;

import hello.AllInShop.domain.Member;
import hello.AllInShop.dto.MemberDTO;
import hello.AllInShop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long register(MemberDTO dto) {

        log.info("DTO-------------------------------------");
        log.info(String.valueOf(dto));

        validateDuplicateMember(dto);

        String password = passwordEncoder.encode(dto.getPassword());

        Member entity = dtoToEntity(dto, password);

        log.info(String.valueOf(entity));

        memberRepository.save(entity);

        return entity.getId();

    }

    private void validateDuplicateMember(MemberDTO dto) {
        Optional<Member> findMembers = memberRepository.findByEmail(dto.getEmail());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
