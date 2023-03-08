package hello.AllInShop.service;

import hello.AllInShop.domain.*;
import hello.AllInShop.dto.PageRequestDTO;
import hello.AllInShop.dto.PageResultDTO;
import hello.AllInShop.dto.ProductDTO;
import hello.AllInShop.dto.ProductImageDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ProductService {

    Long register(ProductDTO dto);

    PageResultDTO<ProductDTO, Object[]> getList(PageRequestDTO requestDTO);

    ProductDTO read(Long id);

    void removeWithReplies(Long id);

    void modify(ProductDTO dto);

    default Map<String, Object> dtoTonEntity(ProductDTO dto) {

        Map<String, Object> entityMap = new HashMap<>();

        Member member = Member.builder().id(dto.getMemberId()).build();
        Brand brand = Brand.builder().id(dto.getBrandId()).build();
        Category category = Category.builder().id(dto.getCateId()).build();

        Product product = Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .gender(dto.getGender())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .writer(member)
                .brand(brand)
                .category(category)
                .build();

        entityMap.put("product", product);

        List<ProductImageDTO> imageDTOList = dto.getImageDTOList();

        //ProductImageDTO 처리
        if(imageDTOList != null && imageDTOList.size() >0 ) {
            List<ProductImage> productImageList = imageDTOList.stream().map(
                    productImageDTO -> {

                        ProductImage productImage = ProductImage.builder().
                            path(productImageDTO.getPath())
                                .imgName(productImageDTO.getImgName())
                                .uuid(productImageDTO.getUuid())
                                .product(product)
                                .build();
                        return productImage;
            }).collect(Collectors.toList());

            entityMap.put("imgList", productImageList);
        }

        return entityMap;
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
