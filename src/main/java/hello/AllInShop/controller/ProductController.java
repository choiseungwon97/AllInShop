package hello.AllInShop.controller;

import hello.AllInShop.dto.PageRequestDTO;
import hello.AllInShop.dto.ProductDTO;
import hello.AllInShop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/product")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public String index() {
        return "redirect:/product/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        log.info("list===================" + pageRequestDTO);

        model.addAttribute("result", productService.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public String register(Model model) {
        log.info("register get .........................");
        model.addAttribute("dto", new ProductDTO());
        return "/product/register";
    }

    @PostMapping("/register")
    public String registerPost(ProductDTO dto, RedirectAttributes redirectAttributes) {

        log.info("dto............." + dto);

        Long id = productService.register(dto);

        redirectAttributes.addFlashAttribute("msg", id);

        return "redirect:/product/list";
    }

    @GetMapping("/read")
    public void read(long id, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {

        log.info("id: " + id);

        ProductDTO dto = productService.read(id);

        model.addAttribute("dto", dto);

    }

    @GetMapping("/modify")
    public void modify(long id, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {

        log.info("id: " + id);

        ProductDTO dto = productService.read(id);

        model.addAttribute("dto", dto);

    }

    @PostMapping("/modify")
    public String modifyPost(ProductDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                             RedirectAttributes redirectAttributes) {

        log.info("post modify ==========================================");
        log.info("dto:" + dto);

        productService.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("id", dto.getId());

        return "redirect:/product/read";
    }

    /*@PostMapping("/remove")
    public String remove(long id, RedirectAttributes redirectAttributes) {

        log.info("id:" + id);

        productService.remove(id);

        redirectAttributes.addFlashAttribute("msg", id);

        return "redirect:/product/list";
    }*/

}
