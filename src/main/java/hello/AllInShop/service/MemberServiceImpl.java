package hello.AllInShop.service;

import hello.AllInShop.domain.Member;
import hello.AllInShop.dto.MemberDTO;
import hello.AllInShop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        String password = passwordEncoder.encode(dto.getPassword());

        Member entity = dtoToEntity(dto, password);

        log.info(String.valueOf(entity));

        memberRepository.save(entity);

        return entity.getId();

    }
}
