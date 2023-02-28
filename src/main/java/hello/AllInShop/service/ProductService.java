package hello.AllInShop.service;

import hello.AllInShop.domain.Brand;
import hello.AllInShop.domain.Category;
import hello.AllInShop.domain.Member;
import hello.AllInShop.domain.Product;
import hello.AllInShop.dto.PageRequestDTO;
import hello.AllInShop.dto.PageResultDTO;
import hello.AllInShop.dto.ProductDTO;

public interface ProductService {

    Long register(ProductDTO dto);

    PageResultDTO<ProductDTO, Object[]> getList(PageRequestDTO requestDTO);

    ProductDTO read(Long id);

    void removeWithReplies(Long id);

    void modify(ProductDTO dto);

    default Product dtoTonEntity(ProductDTO dto) {

        Member member = Member.builder().id(dto.getMemberId()).build();
        Brand brand = Brand.builder().id(dto.getBrandId()).build();
        Category category = Category.builder().id(dto.getCateId()).build();

        Product entity = Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .gender(dto.getGender())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .writer(member)
                .brand(brand)
                .category(category)
                .build();
        return entity;
    }
    default ProductDTO entityToDto(Product product, Brand brand,Category category,Member member, Long replyCount) {
        ProductDTO dto = ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .gender(product.getGender())
                .price(product.getPrice())
                .stock(product.getStock())
                .memberId(member.getId())
                .nickName(member.getNickname())
                .brandId(brand.getId())
                .brandName(brand.getBrandName())
                .cateId(category.getId())
                .cateName(category.getCateName())
                .replyCount(replyCount.intValue())
                .regDate(product.getRegDate())
                .modDate(product.getModDate())
                .build();

        return dto;
    }

}
