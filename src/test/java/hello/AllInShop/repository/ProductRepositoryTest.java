package hello.AllInShop.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.AllInShop.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static hello.AllInShop.domain.Gender.남성;
import static hello.AllInShop.domain.Gender.여성;
import static hello.AllInShop.domain.QBrand.brand;
import static hello.AllInShop.domain.QCategory.category;
import static hello.AllInShop.domain.QProduct.product;

@SpringBootTest
@Transactional
class ProductRepositoryTest {

    @PersistenceContext
    EntityManager em;

    JPAQueryFactory queryFactory;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BrandRepository brandRepository;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);

        Category categoryA = new Category("아우터");
        Brand brandA = new Brand("나이키");

        Category categoryB = new Category("하의");
        Brand brandB = new Brand("아디다스");

        Category cateA= categoryRepository.save(categoryA);
        Brand brA = brandRepository.save(brandA);

        Category cateB = categoryRepository.save(categoryB);
        Brand brB = brandRepository.save(brandB);

        for (int i=0; i<100; i++ ) {
            Product productA;
            if (i %2 ==0) {
                productA = new Product("바람막이JPA" + i, 남성, i, i, cateA, brA);
            } else {
                productA = new Product("바람막이JPA" + i, 남성, i, i, cateB, brB);
            }
            productRepository.save(productA);

        }

    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testEntity() {
        Category categoryA = new Category("아우터");
        Brand brandA = new Brand("나이키");

        em.persist(categoryA);
        em.persist(brandA);

        Product productA = new Product("바람막이", 남성, 10000, 300, categoryA, brandA);

        em.persist(productA);

        em.flush();
        em.clear();

        List<Product> product = em.createQuery("select p from Product p", Product.class)
                .getResultList();

        for (Product product1 : product) {
            System.out.println("product1 = " + product1);
        }
    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void testJPAEntity() {
        Category categoryA = new Category("아우터JPA");
        Brand brandA = new Brand("나이키JPA");

        Category cate= categoryRepository.save(categoryA);
        Brand br = brandRepository.save(brandA);

        Product productA = new Product("바람막이JPA", 남성, 10000, 300, cate, br);

        productRepository.save(productA);

        List<Product> product = em.createQuery("select p from Product p", Product.class)
                .getResultList();

        for (Product product1 : product) {
            System.out.println("product1 = " + product1);
        }
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testUpdateJPAEntity() {
        Category categoryA = new Category("아우터JPA");
        Brand brandA = new Brand("나이키JPA");

        Category cate= categoryRepository.save(categoryA);
        Brand br = brandRepository.save(brandA);

        Product productA = new Product("바람막이JPA", 남성, 10000, 300, cate, br);

        Product updateProduct = productRepository.save(productA);

        updateProduct.changeName("update바람막이");
        updateProduct.changeGender(여성);
        updateProduct.changePrice(20000);
        updateProduct.changeStock(10);


        List<Product> product = em.createQuery("select p from Product p", Product.class)
                .getResultList();

        for (Product product1 : product) {
            System.out.println("product1 = " + product1);
        }
    }

    @Test
    public void QueryDsl() {
        Product findProduct = queryFactory
                .select(product)
                .from(product)
                .where(product.name.eq("바람막이JPA5"))
                .fetchOne();

        System.out.println("findProduct = " + findProduct);
    }

    @Test
    public void QueryDslJoin() {
        QBrand brand = QBrand.brand;
        QCategory category = QCategory.category;

        List<Tuple> productList = queryFactory
                .select(product, brand.brandName, category.cateName)
                .from(product)
                .join(product.brand, brand).fetchJoin()
                .join(product.category, category).fetchJoin()
                .where(brand.brandName.eq("나이키"))
                .fetch();

        for (Tuple product1 : productList) {
            System.out.println("product1 = " + product1);
        }

    }

    @Test
    public void QueryDslDynamic() {
        QBrand brand = QBrand.brand;
        QCategory category = QCategory.category;

        Integer stockParam = 20;
        Integer priceParam = 20;

        List<Tuple> result = searchParam(stockParam, priceParam);

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }


    }

    private List<Tuple> searchParam(Integer stockCond, Integer priceCond) {
       return queryFactory
                .select(product, brand.brandName, category.cateName)
                .from(product)
                .join(product.brand, brand).fetchJoin()
                .join(product.category, category).fetchJoin()
                .where(stockEq(stockCond), priceEq(priceCond))
                .fetch();
    }
    private BooleanExpression stockEq(Integer stockCond) {
        return stockCond != null ? product.stock.eq(stockCond) : null;
    }

    private BooleanExpression priceEq(Integer priceCond) {
        return priceCond != null ? product.price.eq(priceCond) : null;
    }
}