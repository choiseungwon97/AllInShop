package hello.AllInShop.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Slf4j
@Getter
@Setter
@ToString
public class AuthMemberDTO extends User implements OAuth2User {

    private Long id;

    private String email;

    private String password;

    private String nickname;

    private boolean fromSocial;

    private Map<String, Object> attr;

    //소셜로그인용
    //OAuth2User는 Map 타입으로 모든 인증 결과를 atttibutes라는 이름으로 갖고 있기 때문이다.
    public AuthMemberDTO(Long id, String username, String password, boolean fromSocial,
                             Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr) {
        this(id, username,password, fromSocial, authorities);
        this.attr = attr;
    }

    //기존로그인
    public AuthMemberDTO(Long id, String username, String password, boolean fromSocial, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
        this.email = username;
        this.password = password;
        this.fromSocial = fromSocial;

    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }

    @Override
    public String getName() {
        return null;
    }
}
