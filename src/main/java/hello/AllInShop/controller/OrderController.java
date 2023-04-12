package hello.AllInShop.controller;

import hello.AllInShop.domain.Address;
import hello.AllInShop.domain.Member;
import hello.AllInShop.dto.*;
import hello.AllInShop.repository.MemberRepository;
import hello.AllInShop.service.MemberService;
import hello.AllInShop.service.OrderService;
import hello.AllInShop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Controller
@RequestMapping("/order")
@Validated
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ProductService productService;

    @GetMapping("/register")
    @PreAuthorize("hasRole('USER')")
    public String createForm(long id, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model, @AuthenticationPrincipal AuthMemberDTO authMemberDTO) {

        log.info("id: " + id);

        log.info(String.valueOf(authMemberDTO));

        ProductDTO dto = productService.read(id);

        Long memberId = authMemberDTO.getId();
        Optional<Member> member = memberRepository.findById(memberId);

        model.addAttribute("dto", dto);

        model.addAttribute("authDto", authMemberDTO);

        log.info("멤버" + (member));
        log.info("주소"+(member.get().getAddress()));
        model.addAttribute("memberDto", member.get().getAddress());


        return "/order/register";
    }

    @PostMapping("/register")
    public String order(@RequestParam("id") Long id,
                        @AuthenticationPrincipal AuthMemberDTO authMemberDTO,
                        @RequestParam ("count") int count,
                        @RequestParam("city") @NotNull(message = "큰도시는 반드시 입력해야 합니다.") String city,
                        @RequestParam("street") @NotNull(message = "큰도시는 반드시 입력해야 합니다.") String street,
                        @RequestParam("zipcode") @Min(2) int zipcode) {

        Long memberId = authMemberDTO.getId();

        AddressDTO addressDTO = new AddressDTO(city,street,zipcode);


        orderService.register(memberId,id,count, addressDTO.getCity(), addressDTO.getStreet(), addressDTO.getZipcode());

        return "redirect:/product/list";
    }
}