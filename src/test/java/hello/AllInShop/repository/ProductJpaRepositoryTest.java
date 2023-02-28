package hello.AllInShop.repository;

import hello.AllInShop.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ProductJpaRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BrandRepository brandRepository;


    @Test
    public void insertProduct() {

        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
                    .email("user"+i +"@aaa.com")
                    .password("1111")
                    .nickname("USER"+i)
                    .address(new Address("서울시","경수대로",111))
                    .build();


            Category category = Category.builder().cateName("아우터").build();

            Brand brand = Brand.builder().brandName("나이키").build();

            memberRepository.save(member);
            categoryRepository.save(category);
            brandRepository.save(brand);

            Product product = Product.builder()
                    .name("바람막이" + i)
                    .gender(Gender.남성)
                    .price(i)
                    .stock(i)
                    .writer(member)
                    .category(category)
                    .brand(brand)
                    .build();

            productRepository.save(product);
        });

    }

    @Test
    @Transactional
    public void testRead1() {
        Optional<Product> result = productRepository.findById(100L);

        Product product = result.get();

        System.out.println("product = " + product);
        System.out.println("product = " + product.getWriter());
    }
    
    @Test
    public void testReadWithWriter() {
        Object result = productRepository.getProductWithWriter(100L);

        Object[] arr = (Object[]) result;
        System.out.println("Arrays.toString(arr) = " + Arrays.toString(arr));
    }

    @Test
    public void testGetProductWithReply() {
        List<Object[]> result = productRepository.getProductWithReply(100L);

        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void testWithReplyCount() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());

        Page<Object[]> result = productRepository.getProductWithReplyCount(pageable);

        result.get().forEach(row -> {
            Object[] arr = (Object[]) row;

            System.out.println(Arrays.toString(arr));
        });

    }

    @Test
    public void testRead3() {
        Object result = productRepository.getProductById(99L);

        Object[] arr = (Object[]) result;

        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void TestRead4() {
        Object result = productRepository.getProductWithWriterBrandCate(100L);

        Object[] arr = (Object[]) result;

        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testBrand() {
        Brand realBrand = brandRepository.findId(101L);

        System.out.println(realBrand);
    }
}