package hello.AllInShop.service;

import hello.AllInShop.domain.Product;
import hello.AllInShop.dto.PageRequestDTO;
import hello.AllInShop.dto.PageResultDTO;
import hello.AllInShop.dto.ProductDTO;

public interface ProductService {

    Long register(ProductDTO dto);

    PageResultDTO<ProductDTO, Product> getList(PageRequestDTO requestDTO);

    default Product dtoTonEntity(ProductDTO dto) {
        Product entity = Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .gender(dto.getGender())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .build();
        return entity;
    }
    default ProductDTO entityToDto(Product entity) {
        ProductDTO dto = ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .gender(entity.getGender())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }

}
