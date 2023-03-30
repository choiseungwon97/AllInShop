package hello.AllInShop.controller;

import hello.AllInShop.dto.MemberDTO;
import hello.AllInShop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequestMapping("/member")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

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

}
