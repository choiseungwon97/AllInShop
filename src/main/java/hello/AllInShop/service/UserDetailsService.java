package hello.AllInShop.service;

import hello.AllInShop.domain.Grade;
import hello.AllInShop.domain.Member;
import hello.AllInShop.dto.AuthMemberDTO;
import hello.AllInShop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("UserDetailsService loadUserByUsername " + username);

        Optional<Member> result = memberRepository.findByEmail(username, false);

        if(result.isEmpty()) {
            throw new UsernameNotFoundException("이메일 또는 소셜 로그인을 확인해주세요.");
        }

        Member member = result.get();

        log.info("--------------------------------------");
        log.info(String.valueOf(member));


        AuthMemberDTO authMember = new AuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.isFromSocial(),
                member.getGradeSet().stream()
                        .map(gr -> new SimpleGrantedAuthority("ROLE_"+gr.name()))
                        .collect(Collectors.toSet())
        );


        authMember.setNickname(member.getNickname());
        authMember.setFormSocial(member.isFromSocial());
        return authMember;
    }
}
