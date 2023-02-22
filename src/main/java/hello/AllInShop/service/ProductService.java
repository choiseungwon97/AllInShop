package hello.AllInShop.service;

import hello.AllInShop.domain.Product;
import hello.AllInShop.dto.ProductDTO;

public interface ProductService {

    Long register(ProductDTO dto);

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
}
