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


        //?????? ?????????
        /*Pageable pageable = requestDTO.getPageable(Sort.by("id").descending());
        Page<Object[]> result = productRepository.getListPage(pageable);*/

        //???????????? ???
        Page<Object[]> result = productRepository.searchPage(
                requestDTO.getType(),
                requestDTO.getKeyword(),
                requestDTO.getPageable(Sort.by("id").descending()));



        return new PageResultDTO<>(result, fn);
    }

    @Override
    public ProductDTO read(Long id) {

        List<Object[]> result = productRepository.getProductWithAll(id);

        Product product = (Product) result.get(0)[0]; //product ???????????? ?????? ?????? ?????? - ?????? row??? ????????? ???

        Brand brand = (Brand) result.get(0)[1];

        Category category = (Category) result.get(0)[2];

        Member member = (Member) result.get(0)[3];

        List<ProductImage> productImageList = new ArrayList<>(); //????????? ????????? ???????????? productImage?????? ??????

        result.forEach(arr -> {
            ProductImage productImage = (ProductImage) arr[4];
            productImageList.add(productImage);
        });

        Double avg = (Double) result.get(0)[5];

        Long reviewCnt = (Long) result.get(0)[6];

        return entityToDto(product, brand, category,member,productImageList,avg,reviewCnt);
    }

    @Transactional //?????? ????????? ??????????????? ?????? ????????????????????? ????????? ???????????? ??????
    @Override
    public void removeWithReplies(Long id) {

        replyRepository.deleteById(id);
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void modify(ProductDTO dto) {

        //???????????? ?????? ????????? ??????
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

        BooleanExpression expression = qProduct.id.gt(0L); //id >0 ????????? ??????

        booleanBuilder.and(expression);

        if (type == null || type.trim().length() ==0) {
            return booleanBuilder;
        }

        //?????? ?????? ??????
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("n")) {
            conditionBuilder.or(qProduct.name.contains(keyword));
        }
        if (type.contains("g")) {
            conditionBuilder.or(qProduct.gender.stringValue().contains(keyword));
        }

        //?????? ?????? ??????
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }

}
