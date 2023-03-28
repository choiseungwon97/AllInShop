package hello.AllInShop.dto;

import hello.AllInShop.domain.Grade;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Slf4j
@Getter
@Setter
@ToString
public class AuthMemberDTO extends User {

    private String email;

    private String nickname;

    private boolean formSocial;



    public AuthMemberDTO(String username, String password, boolean formSocial, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.formSocial = formSocial;
    }
}
