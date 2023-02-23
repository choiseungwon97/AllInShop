package hello.AllInShop.service;

import hello.AllInShop.domain.Product;
import hello.AllInShop.dto.PageRequestDTO;
import hello.AllInShop.dto.PageResultDTO;
import hello.AllInShop.dto.ProductDTO;
import hello.AllInShop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

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

    @Override
    public PageResultDTO<ProductDTO, Product> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("id").descending());

        Page<Product> result = productRepository.findAll(pageable);

        Function<Product, ProductDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

}
