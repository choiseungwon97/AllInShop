package hello.AllInShop.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import hello.AllInShop.domain.*;
import hello.AllInShop.dto.PageRequestDTO;
import hello.AllInShop.dto.PageResultDTO;
import hello.AllInShop.dto.ProductDTO;
import hello.AllInShop.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ReplyRepository replyRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImgRepository productImgRepository;

    @Transactional
    @Override
    public Long register(ProductDTO dto) {

        log.info("DTO..................................");
        log.info(String.valueOf(dto));

        Map<String, Object> entityMap = dtoTonEntity(dto);
        Product product = (Product) entityMap.get("product");
        List<ProductImage> productImageList = (List<ProductImage>) entityMap.get("imgList");

        productRepository.save(product);

        productImageList.forEach(productImage -> {
            productImgRepository.save(productImage);
        });
        return product.getId();
    }

    @Override
    public PageResultDTO<ProductDTO, Object[]> getList(PageRequestDTO requestDTO) {

        log.info(String.valueOf(requestDTO));

        Function<Object[], ProductDTO> fn =
                (en -> entityToDto(
                        (Product)en[0],
                        (Brand) en[1],
                        (Category) en[2],
                        (Member)en[3],
                        (Long)en[4]));

        /*Page<Object[]> result = productRepository.getProductWithReplyCount(
                requestDTO.getPageable(Sort.by("id").descending()));*/

        Page<Object[]> result = productRepository.searchPage(
                requestDTO.getType(),
                requestDTO.getKeyword(),
                requestDTO.getPageable(Sort.by("id").descending()));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public ProductDTO read(Long id) {

        Object result = productRepository.getProductById(id);

        Object[] arr = (Object[]) result;

        return entityToDto(
                (Product) arr[0],
                (Brand) arr[1],
                (Category) arr[2],
                (Member) arr[3],
                (Long) arr[4]);
    }

    @Transactional //댓글 삭제와 상품삭제는 같이 이루어져야하기 때문에 트랜젝션 추가
    @Override
    public void removeWithReplies(Long id) {

        replyRepository.deleteById(id);
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void modify(ProductDTO dto) {

        //업데이트 하는 항목만 작성
        Optional<Product> result = productRepository.findById(dto.getId());
        Brand updateBrand = brandRepository.findId(dto.getBrandId());
        Category updateCategory = categoryRepository.findId(dto.getCateId());



        if (result.isPresent()) {
            Product entity = result.get();

            entity.changeName(dto.getName());
            entity.changeGender(dto.getGender());
            entity.changePrice(dto.getPrice());
            entity.changeStock(dto.getStock());
            entity.changeBrand(updateBrand);
            entity.changeCategory(updateCategory);

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
