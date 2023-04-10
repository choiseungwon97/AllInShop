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

import java.util.*;
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
    private final ReviewRepository reviewRepository;

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
                        (List<ProductImage>) (Arrays.asList((ProductImage)en[4])),
                        (Double) en[5],
                        (Long) en[6]
                        ));


        //검색 처리전
        /*Pageable pageable = requestDTO.getPageable(Sort.by("id").descending());
        Page<Object[]> result = productRepository.getListPage(pageable);*/

        //검색처리 후
        Page<Object[]> result = productRepository.searchPage(
                requestDTO.getType(),
                requestDTO.getKeyword(),
                requestDTO.getPageable(Sort.by("id").descending()));



        return new PageResultDTO<>(result, fn);
    }

    @Override
    public ProductDTO read(Long id) {

        List<Object[]> result = productRepository.getProductWithAll(id);

        Product product = (Product) result.get(0)[0]; //product 엔티티는 가장 앞에 존재 - 모든 row가 동일한 값

        Brand brand = (Brand) result.get(0)[1];

        Category category = (Category) result.get(0)[2];

        Member member = (Member) result.get(0)[3];

        List<ProductImage> productImageList = new ArrayList<>(); //상품의 이미지 개수만큼 productImage객체 필요

        result.forEach(arr -> {
            ProductImage productImage = (ProductImage) arr[4];
            productImageList.add(productImage);
        });

        Double avg = (Double) result.get(0)[5];

        Long reviewCnt = (Long) result.get(0)[6];

        return entityToDto(product, brand, category,member,productImageList,avg,reviewCnt);
    }

    @Transactional //댓글 삭제와 상품삭제는 같이 이루어져야하기 때문에 트랜젝션 추가
    @Override
    public void removeWithReplies(Long id) {

        reviewRepository.deleteByProductId(id);
        productImgRepository.deleteByProductId(id);
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void modify(ProductDTO dto) {

        //업데이트 하는 항목만 작성
        Map<String, Object> entityMap = dtoTonEntity(dto);
        List<ProductImage> productImageList = (List<ProductImage>) entityMap.get("imgList");
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

            productImageList.forEach(productImage -> {
                productImgRepository.save(productImage);
            });

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
