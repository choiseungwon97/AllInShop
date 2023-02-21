package hello.AllInShop.repository;

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

@SpringBootTest
class ProductRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BrandRepository brandRepository;

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
}