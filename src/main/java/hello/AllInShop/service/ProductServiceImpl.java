package hello.AllInShop.service;

import hello.AllInShop.domain.Product;
import hello.AllInShop.dto.ProductDTO;
import hello.AllInShop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public Long register(ProductDTO dto) {

        log.info("DTO..................................");
        log.info(String.valueOf(dto));

        Product entity = dtoTonEntity(dto);

        log.info(String.valueOf(entity));

        productRepository.save(entity);

        return entity.getId();
    }
}
