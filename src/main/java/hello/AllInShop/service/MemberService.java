package hello.AllInShop.service;

import hello.AllInShop.domain.Address;
import hello.AllInShop.domain.Grade;
import hello.AllInShop.domain.Member;
import hello.AllInShop.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface MemberService {


    Long register(MemberDTO dto);

    default Member dtoToEntity(MemberDTO dto, String password) {
        Member entity = Member.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .password(password)
                .address(new Address(dto.getCity(), dto.getStreet(), dto.getZipcode()))
                .fromSocial(false)
                .build();
        entity.addMemberGrade(Grade.USER);

        return entity;
    }
}
