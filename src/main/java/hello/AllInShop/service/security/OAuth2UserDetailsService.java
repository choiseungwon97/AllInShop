package hello.AllInShop.service.security;

import hello.AllInShop.domain.Address;
import hello.AllInShop.domain.Grade;
import hello.AllInShop.domain.Member;
import hello.AllInShop.dto.AuthMemberDTO;
import hello.AllInShop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserDetailsService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("------------------------------------------");
        log.info("userRequest: " + userRequest);

        //소셜로그인 회사명
        String clientName = userRequest.getClientRegistration().getClientName();

        log.info("clientName: " + clientName);
        log.info(String.valueOf(userRequest.getAdditionalParameters()));

        // 사용자 이메일 등 정보 추출
        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("=================================");
        oAuth2User.getAttributes().forEach((k,v) -> {
            log.info(k +":"+ v);
        });

        String email = null;

        if(clientName.equals("Google")) {
            email = oAuth2User.getAttribute("email");
        }

        log.info("EMAIL: " + email);

        Member member = saveSocialMember(email);

        AuthMemberDTO authMemberDTO = new AuthMemberDTO(
                member.getId(),
                member.getEmail(),
                member.getPassword(),
                true,
                member.getGradeSet().stream().map(
                        grade -> new SimpleGrantedAuthority("ROLE_" + grade.name())
                ).collect(Collectors.toList()),
                oAuth2User.getAttributes()
        );
        authMemberDTO.setNickname(member.getNickname());

        return authMemberDTO;
    }

    private Member saveSocialMember(String email) {

        //기존에 동일한 이메일로 가입한 회원이 있는 경우에는 그대로 조회
        Optional<Member> result = memberRepository.findByEmail(email,true);

        if (result.isPresent()) {
            return result.get();
        }

        //없다면 회원 추가 패스워드는 1111 낙네임은 그냥 이메일 주소로
        Member member = Member.builder().email(email)
                .nickname(email)
                .password(passwordEncoder.encode("1111"))
                .address(new Address("주소를 변경해주세요","",0000))
                .fromSocial(true)
                .build();

        member.addMemberGrade(Grade.USER);

        memberRepository.save(member);

        return member;
    }

}
