package hello.AllInShop.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import hello.AllInShop.domain.Product;
import hello.AllInShop.domain.QProduct;
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

import java.util.Optional;
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

        BooleanBuilder booleanBuilder = getSearch(requestDTO); // 검색 조건 추가

        Page<Product> result = productRepository.findAll(booleanBuilder,pageable);

        Function<Product, ProductDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public ProductDTO read(Long id) {
        Optional<Product> result = productRepository.findById(id);

        return result.isPresent()? entityToDto(result.get()) : null;
    }

    @Override
    public void remove(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void modify(ProductDTO dto) {

        //업데이트 하는 항목만 작성
        Optional<Product> result = productRepository.findById(dto.getId());

        if (result.isPresent()) {
            Product entity = result.get();

            entity.changeName(dto.getName());
            entity.changeGender(dto.getGender());
            entity.changePrice(dto.getPrice());
            entity.changeStock(dto.getStock());

            productRepository.save(entity);
        }
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {

        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QProduct qProduct = QProduct.product;

        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qProduct.id.gt(0L); //id >0 조건만 생성

        booleanBuilder.and(expression);

        if (type == null || type.trim().length() ==0) {
            return booleanBuilder;
        }

        //검색 조건 저장
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("n")) {
            conditionBuilder.or(qProduct.name.contains(keyword));
        }
        if (type.contains("g")) {
            conditionBuilder.or(qProduct.gender.stringValue().contains(keyword));
        }

        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }

}
