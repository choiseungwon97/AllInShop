package hello.AllInShop.controller;

import hello.AllInShop.dto.AuthMemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/home")
public class HomeController {

    //로그인 하지 않아도 접근 가능
    @GetMapping("/all")
    public void all() {
        log.info("All.....................");
    }

    //로그인한 사용자
    @GetMapping("/member")
    public void member(@AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        log.info("Member....................");

        log.info(String.valueOf(authMemberDTO));
    }

    //관리자
    @GetMapping("/admin")
    public void admin() {
        log.info("Admin....................");
    }
}
