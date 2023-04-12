package hello.AllInShop.controller;

import hello.AllInShop.dto.AuthMemberDTO;
import hello.AllInShop.dto.MemberDTO;
import hello.AllInShop.dto.PageRequestDTO;
import hello.AllInShop.repository.MemberRepository;
import hello.AllInShop.service.MemberService;
import hello.AllInShop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/member")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final OrderService orderService;

    /**
     * 회원가입 페이지로 이동
     */
    @GetMapping("/register")
    public String register(Model model) {

        log.info("member register get............................");
        model.addAttribute("dto", new MemberDTO());

        return "/member/register";
    }

    /**
     * 회원가입 정보 저장
     */
    @PostMapping("/register")
    public String registerPost(@Validated @ModelAttribute("dto") MemberDTO dto, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "/member/register";
        }

        log.info("member DTO: " + dto);

        Long id = memberService.register(dto);

        redirectAttributes.addFlashAttribute("msg", id);

        return "redirect:/login";
    }

    /**
     * 회원 주문 마이페이지
     */
    @GetMapping("/myPage")
    @PreAuthorize("hasRole('USER')")
    public String myPage(Model model, @AuthenticationPrincipal AuthMemberDTO authMemberDTO, PageRequestDTO pageRequestDTO) {

        Long id = authMemberDTO.getId();

        log.info("list................" + pageRequestDTO);

        model.addAttribute("result", orderService.getList(id, pageRequestDTO));


        return "/member/myPage";

    }

}
